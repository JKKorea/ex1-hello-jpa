package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.rmi.MarshalException;

public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			// 비영속
//			Member member = new Member();
//			member.setId(101L);
//			member.setName("HelloJPA");

			// 영속
//			System.out.println("=== BEFORE ===");
//			em.persist(member);
//			System.out.println("=== AFTER ===");

			// 이때 1차캐시에 있기때문에 select 쿼리가 db쪽으로 전송되지 않는다.
			Member findMember = em.find(Member.class, 100L);
			findMember.setName("ZZZZZ");
			// 더티체킹(1차캐시와 스냅샵을 비교해서 쿼리 update 생성)

			System.out.println("findMember.id = " + findMember.getId());
			System.out.println("findMember.name = " + findMember.getName());

			// 비영속 상태로 이동시킴
//			em.detach(member);

			// 1차 캐시는 트랜잭션 단위로 관리되고 트랜잭션이 끝나면 1차캐시는 없어짐.
			// 따라서 실질적인 성능 이점 효과 미비.

			// 이 시점에 db에 쿼리 달림
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
