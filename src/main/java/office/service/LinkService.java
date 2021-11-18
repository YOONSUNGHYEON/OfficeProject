package office.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.cooperationProduct.LinkReqeust;
import office.entity.CooperationProduct;
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


	/*public void renewalLowestPrice(String standardProductSeq) {
		findLowestPrice(standardProductSeq);
		standardProductRepository.save(null);
	}

	public boolean findLowestPrice() {

	}

	@Transactional
	public boolean linkProduct(LinkReqeust linkReqeust){
		link(linkReqeust);
		renewalLowestPrice(linkReqeust.getStandardProductSeq());
	}

	@Transactional
	public boolean uninkProduct(LinkReqeust linkReqeust){
		unlink(linkReqeust);
		renewalLowestPrice(linkReqeust.getStandardProductSeq());
	}*/


	/**
	 * 협력사 상품에 기준상품 링크 하기
	 *
	 * @param linkReqeust
	 * @return boolean
	 */
	public boolean link(LinkReqeust linkReqeust) {
		// 현재 기준 상품과 협력사 상품 사이에 링크가 있는지의 여부 확인
		Link tempLink = linkRepository.findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq(linkReqeust.getStandardProductSeq(), linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
		if(tempLink!=null) {
			return false;
		} else {
			//기준상품과 협력사 상품 사이의 링크 테이블 생성
			StandardProduct standardProduct = standardProductRepository.getOne(linkReqeust.getStandardProductSeq());
			CooperationProduct cooperationProduct = cooperationProductRepository.getOne(linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
			log.info(standardProduct.getName());
			Link newLink = new Link(standardProduct, cooperationProduct);
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
	public boolean unlink(LinkReqeust linkReqeust) {
		// 현재 기준 상품과 협력사 상품 사이에 링크가 있는지의 여부 확인
		Link link = linkRepository.findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq(linkReqeust.getStandardProductSeq(), linkReqeust.getCooperationProductSeq(), linkReqeust.getCooperationCompanySeq());
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
