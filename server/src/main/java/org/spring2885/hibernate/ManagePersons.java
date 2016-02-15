package org.spring2885.hibernate;

import java.util.*;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManagePersons {
	private static SessionFactory factory;
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]){
		try{
			factory = new AnnotationConfiguration().
	                   configure().
	                   //addPackage("com.xyz") //add package if used.
	                   addAnnotatedClass(Person.class).
	                   buildSessionFactory();
		}catch (Throwable ex){
			System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
		}
		
		ManagePersons mp = new ManagePersons();
		Integer perId1 = mp.addPerson(1, "Matthew");
		mp.listPersons();
	}
	
	public Integer addPerson(int id, String personName){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer personId = null;
		try{
			tx = session.beginTransaction();
			Person p = new Person();
			p.setId(id);
			p.setPersonName(personName);
			personId = (Integer) session.save(p);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	         	e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }
		
		return personId;
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
