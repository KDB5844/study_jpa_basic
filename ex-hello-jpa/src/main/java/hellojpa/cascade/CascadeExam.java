package hellojpa.cascade;

import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CascadeExam {

    public static void main(String[] args) {

        JpaTemplate jpaTemplate = new JpaTemplate() {
            @Override
            public void execute(EntityManager em) {
                Parent parent = new Parent();

                Child child1 = new Child();
                Child child2 = new Child();

                parent.addChild(child1);
                parent.addChild(child2);

                em.persist(parent);

                em.flush();
                em.clear();

                Parent findParent = em.find(Parent.class, parent.getId());
                findParent.getChildList().remove(0);
            }
        };

        jpaTemplate.transaction();

    }

}
