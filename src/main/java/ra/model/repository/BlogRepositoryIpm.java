package ra.model.repository;

import org.springframework.transaction.annotation.Transactional;
import ra.model.enttity.Blog;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Transactional
public class BlogRepositoryIpm implements IBlogRepository{
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Blog> findAll() {
        TypedQuery<Blog> query=em.createQuery("select b from Blog b",Blog.class);
        return query.getResultList();
    }

    @Override
    public Blog findById(Long id) {
        TypedQuery<Blog> query=em.createQuery("select b from Blog b where b.id =:id",Blog.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void save(Blog blog) {
        if (blog.getId()!=null){
            em.merge(blog);
        }else {
            em.persist(blog);
        }

    }

    @Override
    public void remove(Long id) {
        Blog b= findById(id);
        if (b!=null){
            em.remove(b);
        }
    }
}
