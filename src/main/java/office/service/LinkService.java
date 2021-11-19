package office.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import office.dto.cooperationProduct.LinkReqeust;
import office.entity.Link;
import office.entity.StandardProduct;
import office.repository.CooperationProductRepository;
import office.repository.LinkRepository;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class LinkService {
	private static final Logger log = LoggerFactory.getLogger(LinkService.class);
	private final LinkRepository linkRepository;
	private final StandardProductRepository standardProductRepository;
	private final CooperationProductRepository cooperationProductRepository;


	public void renewalLowestPrice(String standardProductSeq) {
		findLowestPrice(standardProductSeq);
		standardProductRepository.save(newStandardProduct);
	}

	/**
	 * 최저가 갱신 프로그램
	 *
	 * @param standardProductSeq 갱신할 기준 상품 seq
	 */
	public void findLowestPrice(String standardProductSeq) {
		StandardProduct standardProduct = standardProductRepository.getOne(standardProductSeq);
		ArrayList<Link> linkList = linkRepository.findByStandardProductSeq(standardProductSeq);

		for (int i = 0; i < linkList.size(); i++) {
			log.info(linkList.get(i).getCooperationProduct().getCooperationProductSeq());
		}


		/*
		int minPrice = 0;
		int minMobilePrice = 0;
		int averagePrice = 0;
		for (int i = 0; i < cooperationProductList.size(); i++) {
			averagePrice += cooperationProductList.get(i).getPrice();
			if (i == 0) {
				minPrice = cooperationProductList.get(0).getPrice();
				minMobilePrice = cooperationProductList.get(0).getMobilePrice();
			} else {
				if (minPrice > cooperationProductList.get(i).getPrice()) {
					minPrice = cooperationProductList.get(i).getPrice();
				} else if (minMobilePrice > cooperationProductList.get(i).getMobilePrice()) {
					minMobilePrice = cooperationProductList.get(i).getMobilePrice();
				}
			}
		}
		try {
			averagePrice = averagePrice / cooperationProductList.size();
		} catch (ArithmeticException e) {
			averagePrice = 0;
		}
		StandardProduct newStandardProduct = standardProduct.updatePrice(StandardProduct.builder().cooperationCompanyCount(cooperationProductList.size()).lowestPrice(minPrice).mobileLowestPrice(minMobilePrice).averagePrice(averagePrice).combinedLowestPrice(minPrice < minMobilePrice ? minPrice : minMobilePrice).build());
		return newStandardProduct;
		*/
	}
	@Transactional
	public boolean link(LinkReqeust linkReqeust){
		linkProduct(linkReqeust);
		//renewalLowestPrice(linkReqeust.getStandardProductSeq());
		return true;
	}

	@Transactional
	public boolean unink(LinkReqeust linkReqeust){
		unlinkProduct(linkReqeust);
		//renewalLowestPrice(linkReqeust.getStandardProductSeq());
		return true;
	}


	/**
	 * 협력사 상품에 기준상품 링크 하기
	 *
	 * @param linkReqeust
	 * @return boolean
	 */
	public boolean linkProduct(LinkReqeust linkReqeust) {
		// 현재 기준 상품과 협력사 상품 사이에 링크가 있는지의 여부 확인

		Link tempLink = linkRepository.findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq(linkReqeust.getStandardProductSeq(), linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
		if(tempLink!=null) {
			return false;
		} else {
			//기준상품과 협력사 상품 사이의 링크 테이블 생성
			//StandardProduct standardProduct = standardProductRepository.getOne(linkReqeust.getStandardProductSeq());
			//CooperationProduct cooperationProduct = cooperationProductRepository.getOne(new CooperationProductID());
			//log.info(standardProduct.getName());
			Link newLink = new Link(linkReqeust.getStandardProductSeq(), linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
			linkRepository.save(newLink);
		}
		return true;
	}

	/**
	 * 협력사 상품에 기준상품 링크 해제 하기
	 *
	 * @param linkReqeust
	 * @return boolean
	 */
	public boolean unlinkProduct(LinkReqeust linkReqeust) {
		// 현재 기준 상품과 협력사 상품 사이에 링크가 있는지의 여부 확인
		Link link = linkRepository.findByStandardProductSeqAndCooperationProductSeqAndCooperationCompany(linkReqeust.getStandardProductSeq(), linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
		if(link==null) {
			return false;
		} else {
			//기준상품과 협력사 상품 사이의 링크 테이블 삭제
			linkRepository.delete(link);
			log.info("링크 삭제 성공");
		}
		return true;
	}
}
