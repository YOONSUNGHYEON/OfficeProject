package office.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{

	Link findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq(String standardProductSeq, String cooperationProductSeq, String cooperationCompanySeq);



	ArrayList<Link> findByStandardProductSeq(String standardProductSeq);
	ArrayList<Link> findByCooperationProductSeq(String cooperationProductSeq);
}
