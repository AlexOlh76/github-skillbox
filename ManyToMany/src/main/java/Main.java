import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Course course = session.get(Course.class, 1); //SELECT
//        System.out.println(course.getStudents().size()); // [1]
//        List<Student> studentList = course.getStudents(); // [2]
//        for(Student student : studentList){
//            System.out.println(student.getName());
//        }
        List<Student> studentList = course.getStudents(); // [3]
        studentList.forEach(x->System.out.println(x.getName()));

        transaction.commit();
        sessionFactory.close();
    }
}
