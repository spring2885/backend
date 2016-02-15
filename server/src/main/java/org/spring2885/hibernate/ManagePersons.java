package org.spring2885.hibernate;

import java.util.*;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ManagePersons {
	private static SessionFactory factory;
	private static Configuration config;
	
	//@SuppressWarnings("deprecation")
	public static void main(String args[]){
		try{
			/*factory = new AnnotationConfiguration().
	                   configure().
	                   //addPackage("com.xyz") //add package if used.
	                   addAnnotatedClass(Person.class).
	                   buildSessionFactory();*/
			config = new Configuration().configure().addAnnotatedClass(Person.class);
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();
             
            // builds a session factory from the service registry
            factory = config.buildSessionFactory(serviceRegistry);  
			
            //Original 2.0
            /*factory = new Configuration().
						configure().
						addAnnotatedClass(Person.class).
						buildSessionFactory();*/
		}catch (Throwable ex){
			System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
		}
		
		ManagePersons mp = new ManagePersons();
		/*Integer perId1 =*/ mp.addPerson(2, 
									"Matthew", 
									475144, 
									null, 
									null, 
									null, 
									null, 
									null, 
									null, 
									null, 
									null, 
									null, 
									1,
									null, 
									null, 
									null);
		mp.listPersons();
	}
	
	public /*Integer*/ void addPerson(int id, 
							String personName,
							int studentId,
							String title,
							String aboutMe,
							String resumeUrl,
							String personImageUrl,
							String personEmail,
							String personPhone,
							String personOccupation,
							String companyName,
							Date personBirthdate,
							int personType,
							Date lastLogon,
							String passwordSha,
							String passwordSalt){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer personId = null;
		try{
			tx = session.beginTransaction();
			Person p = new Person();
			p.setId(id);
			p.setPersonName(personName);
			//personId = (Integer) session.save(p);
			session.save(p);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	         	e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }
		
		//return personId;
	}
	
	public void listPersons(){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List persons = session.createQuery("FROM Person").list(); 
	         for (Iterator iterator = 
	                           persons.iterator(); iterator.hasNext();){
	            Person person = (Person) iterator.next(); 
	            System.out.print("ID: " + person.getId()); 
	            System.out.print("Name: " + person.getPersonName()); 
	
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
}
