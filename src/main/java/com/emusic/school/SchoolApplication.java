package com.emusic.school;

import com.emusic.school.models.Client;
import com.emusic.school.models.Merch;
import com.emusic.school.models.PurchaseOrder;
import com.emusic.school.models.Ticket;
import com.emusic.school.repositories.ClientRepository;
import com.emusic.school.repositories.MerchRepository;
import com.emusic.school.repositories.PurchaseOrderRepository;
import com.emusic.school.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.emusic.school.models.*;
import com.emusic.school.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.emusic.school.models.MerchWaist.DEFAULT;
import static com.emusic.school.models.MerchWaist.m;

@SpringBootApplication
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initDate(ClientRepository clientRepository, MerchRepository merchRepository,
									  TicketRepository ticketRepository, PurchaseOrderRepository purchaseOrderRepository,
									  CourseRepository courseRepository,CourseTicketRepository courseTicketRepository,
									  TeacherRepository teacherRepository) {
		return (args) -> {


			Client client1 = new Client("Juan","Perez","juanperez@gmail.com", passwordEncoder.encode("1234"), true,true);
			client1.setReviewCourse("Honestly I had an excellent learning in such a short time, the teachers are super friendly and great connoisseurs of music and rock. Thank you!");
			client1.setCourse("Guitar");


			Client client2 = new Client("Paul","Gray","paulgray@gmail.com", passwordEncoder.encode("1234"), true,true);
			client2.setReviewCourse("Since ays wanted to pI was a child I alwlay the guitar, thanks to the guitar course that has become a reality. I am very happy with the quality of information and practical exercises that I have been given at the academy.");
			client2.setCourse("Guitar");


			Client client3 = new Client("Emilia","Bailey","emiliabailey@gmail.com", passwordEncoder.encode("1234"), true,true);
			client3.setReviewCourse("I started taking classes 2 weeks ago. For the moment very happy. They are very didactic and with excellent predisposition.");
			client3.setCourse("Sax");


			Client client4 = new Client("Riley","Douglas","rileydouglas@gmail.com", passwordEncoder.encode("1234"), true,true);
			client4.setReviewCourse("Thanks to the piano course of this academy, I can fulfill my dream of playing professionally in a rock band, excellent quality of teachers");
			client4.setCourse("Piano");
			Client clientAdmin = new Client("admin", "admin", "admin@admin.com" , passwordEncoder.encode("1234"), true,true);
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);
			clientRepository.save(clientAdmin);

			Teacher teacher = new Teacher("Mike","Portnoy","mikeportnoy@gmail.com",passwordEncoder.encode("123456"), "Drums",true);
			teacherRepository.save(teacher);
			Teacher teacher1 = new Teacher("Robert","Thompson","mikethompson@gmail.com",passwordEncoder.encode("123456"), "Guitar",true);
			teacherRepository.save(teacher1);
			Teacher teacher2 = new Teacher("Richard","Kotzen","richiekotzen@gmail.com",passwordEncoder.encode("123456"), "Sing",true);
			teacherRepository.save(teacher2);
			Teacher teacher3 = new Teacher("Tom","Morello","tommorello@gmail.com",passwordEncoder.encode("123456"), "Bass",true);
			teacherRepository.save(teacher3);
			Teacher teacher4 = new Teacher("Maynard","Keenan","maynardkeenan@gmail.com",passwordEncoder.encode("123456"), "Piano",true);
			teacherRepository.save(teacher4);
			Teacher teacher5 = new Teacher("Joey","Jordison","joeyjordison@gmail.com",passwordEncoder.encode("123456"), "Sax",true);
			teacherRepository.save(teacher5);


			Course course = new Course("beginner","Guitar",4,20000D,20,true,teacher1);
			courseRepository.save(course);
			Course course1 = new Course("beginner", "Drums", 4, 30000D, 23, true,teacher);
			courseRepository.save(course1);
			Course course2 = new Course("beginner", "Sing", 8, 18500D, 25, true,teacher2);
			courseRepository.save(course2);
			Course course3 = new Course("expert", "Sax", 6, 15900D, 18, true,teacher5);
			courseRepository.save(course3);
			Course course4 = new Course("middle", "Bass", 6, 18500D, 21, true,teacher3);
			courseRepository.save(course4);
			Course course5 = new Course("expert", "Piano", 10, 12000D, 20, true,teacher4);
			courseRepository.save(course5);


			String urlImageTshirt = "./assets/tshirt-img.png";
			String urlImageCap = "./assets/cap-img.png";
			String urlImageLogbook = "./assets/logbook-img.png";
			String urlImageCover = "./assets/cover-img.png";
			String urlImageKeyring = "./assets/keyring-img.png";
			String urlImageCup = "./assets/cup-img.png";

			Merch merch1= new Merch(10,"cap",650,DEFAULT,true,urlImageCap);
			merchRepository.save(merch1);
			Merch merch2= new Merch(10,"t-shirt",1200,m,true,urlImageTshirt);
			merchRepository.save(merch2);
			Merch merch3= new Merch(10,"key-ring",250,m,true,urlImageKeyring);
			merchRepository.save(merch3);
			Merch merch4= new Merch(10,"logbook",420,m,true,urlImageLogbook);
			merchRepository.save(merch4);
			Merch merch5= new Merch(10,"cover",3700,m,true,urlImageCover);
			merchRepository.save(merch5);
			Merch merch6= new Merch(10,"cup",450,m,true,urlImageCup);
			merchRepository.save(merch6);
			Merch merch7= new Merch(10,"t-shirt",1200,m,true, urlImageTshirt);
			merchRepository.save(merch7);
			Merch merch8= new Merch(10,"cap",650,m,true, urlImageCap);
			merchRepository.save(merch8);


			Ticket ticket1 = new Ticket(500,client1);
			Ticket ticket3 = new Ticket(500,client3);
			Ticket ticket4 = new Ticket(500,client4);


			ticketRepository.save(ticket1);
			ticketRepository.save(ticket3);
			ticketRepository.save(ticket4);

			CourseTicket courseTicket = new CourseTicket(ticket1,course);
			CourseTicket courseTicket2 = new CourseTicket(ticket3,course2);
			CourseTicket courseTicket3 = new CourseTicket(ticket4,course3);


			courseTicketRepository.save(courseTicket);
			courseTicketRepository.save(courseTicket2);
			courseTicketRepository.save(courseTicket3);


			Ticket ticket2 = new Ticket(400,client1);
			ticketRepository.save(ticket2);
			Ticket ticket5 = new Ticket(300,client1);
			ticketRepository.save(ticket5);
			Ticket ticket6 = new Ticket(1000,client1);
			ticketRepository.save(ticket6);

			CourseTicket courseTicket4 = new CourseTicket(ticket5,course2);
			CourseTicket courseTicket5 = new CourseTicket(ticket6,course5);
			courseTicketRepository.save(courseTicket4);
			courseTicketRepository.save(courseTicket5);


			PurchaseOrder purchaseOrder = new PurchaseOrder(ticket1,merch1,2);
			PurchaseOrder purchaseOrder2 = new PurchaseOrder(ticket2,merch2,1);
			PurchaseOrder purchaseOrder3 = new PurchaseOrder(ticket6,merch5,2);
			PurchaseOrder purchaseOrder4 = new PurchaseOrder(ticket5,merch3,3);

			purchaseOrderRepository.save(purchaseOrder);
			purchaseOrderRepository.save(purchaseOrder2);
			purchaseOrderRepository.save(purchaseOrder3);
			purchaseOrderRepository.save(purchaseOrder4);



			System.out.println("---------------------------------");
			System.out.println("EMusic App iniciada, Let's Rock! ");
			System.out.println("---------------------------------");
		};
	}
}
