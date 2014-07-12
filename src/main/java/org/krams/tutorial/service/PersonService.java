package org.krams.tutorial.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.krams.tutorial.domain.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for processing Persons
 * 
 * @author Krams at {@link http://krams915@blogspot.com
 */
@Service("personService")
@Transactional
public class PersonService {

	protected static Logger logger = Logger.getLogger("service");
	
	private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this. entityManager = entityManager;
    }
    
	/**
	 * Retrieves all persons
	 * 
	 * @return a list of persons
	 */
	public List<Person> getAll() {
		logger.debug("Retrieving all persons");
		
		// Create a JPA query
		Query query  = entityManager.createQuery("FROM Person");
		
		// Retrieve all
		return query.getResultList();
	}
	
	/**
	 * Retrieves a single person
	 */
	public Person get( Integer id ) {
		
		// Retrieve existing person
		Person person = (Person) entityManager.createQuery("FROM Person p where p.id = :id")
        	.setParameter("id", id).getSingleResult();
		
		return person;
	}
	
	/**
	 * Adds a new person
	 */
	public void add(Person person) {
		logger.debug("Adding new person");
		
		// Persists to db
		entityManager.persist(person);
	}
	
	/**
	 * Deletes an existing person
	 * @param id the id of the existing person
	 */
	public void delete(Integer id) {
		logger.debug("Deleting existing person");
		
		// Retrieve existing person
		Person person = this.get(id);
		
		// Delete 
		entityManager.remove(person);
	}
	
	/**
	 * Edits an existing person
	 */
	public void edit(Person person) {
		logger.debug("Editing existing person");
		
		// Retrieve existing person via id
		Person existingPerson = this.get(person.getId());
		
		// Assign updated values to this person
		existingPerson.setFirstName(person.getFirstName());
		existingPerson.setLastName(person.getLastName());
		existingPerson.setMoney(person.getMoney());

		// Save updates
		entityManager.merge(person);
	}
}
