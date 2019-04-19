package com.techelevator.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.sym.Name;
import com.techelevator.model.Book;
import com.techelevator.model.BookPerson;
import com.techelevator.model.Competition;
import com.techelevator.model.CompetitionRequest;
import com.techelevator.model.Goal;
import com.techelevator.model.GoalRequest;
import com.techelevator.model.Person;
import com.techelevator.model.Session;
import com.techelevator.model.SessionRequest;
import com.techelevator.model.Signup;
import com.techelevator.model.User;
import com.techelevator.model.DAO.BookDAO;
import com.techelevator.model.DAO.CompetitionDAO;
import com.techelevator.model.DAO.FamilyDAO;
import com.techelevator.model.DAO.GoalDAO;
import com.techelevator.model.DAO.PersonDAO;
import com.techelevator.model.DAO.SessionDAO;
import com.techelevator.model.DAO.UserDAO;


@RestController
public class RestApiController {
	
	private UserDAO userDAO;
	private FamilyDAO familyDAO;
	private BookDAO bookDAO;
	private PersonDAO personDAO;
	private GoalDAO goalDAO;
	private CompetitionDAO competitionDAO;
	private SessionDAO sessionDAO;
	
	@Autowired
	public RestApiController(UserDAO userDAO, FamilyDAO familyDAO, BookDAO bookDAO, PersonDAO personDAO, GoalDAO goalDAO, CompetitionDAO competitionDAO, SessionDAO sessionDAO) {
		this.userDAO = userDAO;
		this.familyDAO = familyDAO;
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
		this.goalDAO = goalDAO;
		this.competitionDAO = competitionDAO;
		this.sessionDAO = sessionDAO;
	}
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/addperson", method=RequestMethod.POST)
	public void addNewPerson(@RequestBody Person person) {
		personDAO.addPerson(person.getFamilyId(), person.getName(), person.isParent(), false);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/authenticate", method=RequestMethod.POST)
	public Long authenticateLogin(@RequestBody User user) {
		if(userDAO.searchForUsernameAndPassword(user.getUserName(), user.getPassword())) {
			return userDAO.getFamilyByUser(user);
		} else {
			return null;
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/signup", method=RequestMethod.POST)
	public long addNewUser(@RequestBody Signup signup) {
		long familyId = familyDAO.addFamily(signup);
		userDAO.saveUser(signup.getUserName(), signup.getPassword(), familyId);
		return familyId;
	}
	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/booklist", method=RequestMethod.GET)
	public List<Book> getListOfBooks(@RequestParam long personId){
		return bookDAO.getListOfBooksByPerson(personId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/booklistbyfamily", method=RequestMethod.GET)
	public List<Book> getListOfBooksByFamily(@RequestParam long familyId){
		return bookDAO.getListOfBooksByFamily(familyId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/bookdetail", method=RequestMethod.GET)
	public Book getBookDetail(@RequestParam long bookId) {
		return bookDAO.getBookDetail(bookId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/familylist", method=RequestMethod.GET)
	public List<Person> getFamilyList(@RequestParam long familyId){
		return personDAO.getListOfPeopleInFamily(familyId);
		}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/goaldetail", method=RequestMethod.GET)
	public Goal getGoalDetail(@RequestParam long familyId) {
		return goalDAO.getGoalDetail(familyId);
	}
	

	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/competitiondetail", method=RequestMethod.GET)
	public Competition getCompetitionDetail(@RequestParam long familyId) {
		return competitionDAO.showCompetitionDetails(familyId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/competitionlistperson", method=RequestMethod.GET)
	public List<Competition> getListOfActiveCompetitionsByPerson(@RequestParam long personId) {
		LocalDate todayDate = LocalDate.now();
		return competitionDAO.getListOfCompetitionsByPerson(todayDate, personId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/competitionlistfamily", method=RequestMethod.GET)
	public List<Competition> getListOfActiveCompetitionsByFamily(@RequestParam long familyId) {
		LocalDate today = LocalDate.now();
		return competitionDAO.getListOfActiveCompetitionsByFamily(today, familyId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/goallist", method=RequestMethod.GET)
	public List<Goal> getGoalList(@RequestParam long personId) {
		return goalDAO.getListOfAllGoals(personId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/goallistprogress", method=RequestMethod.GET)
	public int getTotalMinutesReadByPersonAndGoal(@RequestParam long personId, @RequestParam long goalId) {
		return goalDAO.getTotalMinutesReadByPersonAndGoal(personId, goalId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/goallistbyfamily", method=RequestMethod.GET)
	public List<Goal> getFamilyGoalList(@RequestParam long familyId) {
		return goalDAO.getListOfGoalsByFamily(familyId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/getbooksnotreading", method=RequestMethod.GET)
	public List<Book> getBooksNotReading(@RequestParam long familyId, @RequestParam long personId) {
		return bookDAO.getListOfBooksNotReading(familyId, personId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/addbook", method=RequestMethod.POST)
	public void addBook(@RequestBody Book book) {
		bookDAO.addNewBook(book);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/addpersonbook", method=RequestMethod.POST)
	public void addBookToPerson(@RequestBody BookPerson bookPerson) {
		bookDAO.addBookToPerson(bookPerson.getBook(), bookPerson.getPersonId());
	}
	
	@CrossOrigin(origins= "http://localhost:8081")
	@RequestMapping(path="/addgoal", method=RequestMethod.POST)
	public void addGoal(@RequestBody GoalRequest goal) {
		LocalDate startDate = LocalDate.parse(goal.getStartDate());
		long personId = goal.getPersonId();
		Goal newGoal = new Goal();
		newGoal.setStartDate(startDate);
		newGoal.setNameOfGoal(goal.getNameOfGoal());
		newGoal.setDescription(goal.getDescription());
		newGoal.setMinutesToReachGoal(goal.getMinutesToReachGoal()); 
		newGoal.setNumberOfDays(goal.getNumberOfDays());
		goalDAO.createNewGoal(newGoal, personId);
	
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/addreadingactivity", method=RequestMethod.POST)
	public void addReadingActivity(@RequestBody SessionRequest session) {
		System.out.println(session.getMinutesRead() + " " + session.getIsbn() + " " + session.getPersonId() + " " + session.getDateOfReading());
		LocalDate date = LocalDate.parse(session.getDateOfReading());
		Session newSession = new Session();
		newSession.setDateOfReading(date);
		newSession.setMinutesRead(session.getMinutesRead());
		long peopleBookId = bookDAO.getPeopleBookId(session.getPersonId(), session.getIsbn());
		newSession.setPeopleBookId(peopleBookId);
		newSession.setTypeOfReading(session.getTypeOfReading());
		sessionDAO.addSession(newSession);
		if (session.isFinished()) {
			bookDAO.setBookInactive(session.getPersonId(), session.getIsbn());
		}
	}
	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/addcompetition", method=RequestMethod.POST)
	public void addNewCompetition(@RequestBody CompetitionRequest competition) {
		System.out.println(competition.getNameOfCompetition() + " " + competition.getStartDate() + " " + competition.getEndDate() + " " + competition.getMinutesToFinish() + " " + competition.getFamilyId() + " " + competition.getDescription());
		LocalDate startDate = LocalDate.parse(competition.getStartDate());
		LocalDate endDate = LocalDate.parse(competition.getEndDate());
		Competition newComp = new Competition();
		newComp.setStartDate(startDate);
		newComp.setEndDate(endDate);
		newComp.setDescription(competition.getDescription());
		newComp.setMinutesToFinish(competition.getMinutesToFinish());
		newComp.setNameOfCompetition(competition.getNameOfCompetition());
		newComp.setFamilyId(competition.getFamilyId());
		competitionDAO.createNewCompetition(newComp);
	}
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/deleteperson", method=RequestMethod.POST)
	public void deletePerson(@RequestBody Person person) {
		personDAO.deletePerson(person.getPeopleId());

	}
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/getpersonsession", method=RequestMethod.GET)
	public List<Session> getListOfSessionsByPerson(@RequestParam long personId) {
		return sessionDAO.getListOfSessionsByPerson(personId);
	}
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path="/getfamilysession", method=RequestMethod.GET)
	public List<Session> getListOfSessionsByFamily(@RequestParam long familyId) {
		return sessionDAO.getListOfSessionsByFamily(familyId);
	}
}
