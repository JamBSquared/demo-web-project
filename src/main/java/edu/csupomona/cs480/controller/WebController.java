package edu.csupomona.cs480.controller;

import java.io.File;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
//import com.sun.tools.javac.comp.Todo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.GpsProduct;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.GpsProductManager;
import edu.csupomona.cs480.data.provider.UserManager;

import org.apache.commons.math3.random.RandomDataGenerator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

//package com.mkyong;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import java.io.IOException;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;
	@Autowired
	private GpsProductManager gpsProductManager;


	// Bryan Lee
	@RequestMapping(value = "", method = RequestMethod.GET)
	String testCheckA(){
		updateUser("123", "Bryan Lee", "Computer Science");
		getUser("123");
		getUserHomepage();
		return "Howdy - Bryan Lee";

	}
	private static final String PARENT_DIR = "C:\\Users\\BryanLee\\ProgramFile";

	public void checkFileExistsInDirectoryExists() {
		File randomFile = FileUtils.getFile("C:\\Users\\BryanLee\\ProgramFile\\text.txt");
		File parent = FileUtils.getFile(PARENT_DIR);
		try {
			System.out.println("Does it exist: " + FileUtils.directoryContains(parent, randomFile));

		} catch (IOException io) {
		}
	}
	
	// Arno Aghababyan
	@RequestMapping(value = "/cs480/student/", method = RequestMethod.GET)
	String checkUserB(){
		updateUser("548", "John Smith", "Biology");
		getUser("548");
		getUserHomepage();
		return "Successful Retrieved Homepage";
	}

	@RequestMapping(value = "/cs480/dom4j/", method = RequestMethod.GET)
	String createDocument(){
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");

		Element author1 = root.addElement("author")
				.addAttribute("name", "Bob")
				.addAttribute("location", "USA")
				.addText("Bob Bill");

		return document.toString();
	}

	// Brian Cho
	@RequestMapping(value = "/cs480/student/cho", method = RequestMethod.GET)
	String checkUserC() {
		updateUser("404", "Brian kernighan", "Computer Science");
		getUser("404");
		getUserHomepage();
		return "Hello, World";
	}

	@RequestMapping(value = "/cs480/student/cho2", method = RequestMethod.GET)
	String trimChars(String str){
		// Example Input String: String str = "a,,b,,,,c1,d2";
		Iterable<String> result = Splitter.on(',').trimResults(CharMatcher.DIGIT)
				.omitEmptyStrings()
				.split(str);

		return str;
	}

	// Min-Jae Yi
	@RequestMapping(value = "/cs480/minjae-api", method = RequestMethod.POST)
	String doMath(
			@RequestParam("value_a") String valA,
			@RequestParam("value_b") String valB) {
		int a, b;
		try {
			a = Integer.parseInt(valA);
			b = Integer.parseInt(valB);
		}
		catch (NumberFormatException e) {
			return "Cannot add values due to bad input.\n";
		}
		
		int c = a + b;
		
		return Integer.toString(c) + "\n";
	}

	@RequestMapping(value = "/cs480/random-num", method = RequestMethod.GET)
	String returnRandom() {
		RandomDataGenerator rg = new RandomDataGenerator();
		return rg.nextHexString(15);
	}
	
	//Jack Yan
	@RequestMapping(value = "/cs480/jack", method = RequestMethod.GET)
	String userCheck() {

		// Using jsoup package to grab title from a website
		print("Running...");
		org.jsoup.nodes.Document document = null;
		try {
			//Get Document object after parsing the html from given url.
			document = Jsoup.connect("http://youtube.com/").get();

			String title = document.title(); //Get title
			print("  Title: " + title); //Print title.

		} catch (IOException e) {
			e.printStackTrace();
		}
		print("Done");

		return document.toString();
	}

	public static void print(String string) {
		System.out.println(string);
	}


	
	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK-CS480-Demo";
	}

	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}
	
	@RequestMapping(value = "/cs480/gps/list", method = RequestMethod.GET)
	List<GpsProduct> listGpsProducts() {
		return gpsProductManager.listAllGpsProducts();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

}
