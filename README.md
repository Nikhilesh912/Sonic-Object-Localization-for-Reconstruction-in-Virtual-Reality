
A. Common settings
	• Install latest version of Mongo database 3.6.
	• Install Robomongo or equivalent GUI tool to connect to
	  Mongodb.
	• Create a database called ’OnOffBox’ and collection called ’BoxData’.
	• Install virtual box.
	• Install Docker.


B. System execution
	1) Local single machine run mode:
	• Running the system in this mode is pretty straight for- ward. You have to run two scripts which would start the on|off box simulation module and the receiving server.

	• To start the on|off box simulation run the BoxSimula-
	tion.sh script using command.

	     bash BoxSimulation.sh

	The script will pop up 15 windows each representing one  on|off box.
	
	• Once the simulation is running it will emit data to
	localhost port 40000 using UDP. To receive this data run the script to start the receiving server using following command.
	      
	      bash ReceivingServer.sh

	• Once the receiving server starts receiving data it write the data to MongoDB database called ’OnOffBox’. The database has a collection called BoxData which stores data for all the 25 on|off boxes.
	
	• Finally run the script for visualization which will graph- ically render the on|off boxes.
      bash Visualization.sh
