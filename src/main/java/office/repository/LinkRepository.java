package office.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{

	Link findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq(String standardProductSeq, String cooperationProductSeq, String cooperationCompanySeq);

}
