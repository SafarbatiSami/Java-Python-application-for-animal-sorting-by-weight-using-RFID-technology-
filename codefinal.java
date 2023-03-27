

import java.util.List;
import java.util.Scanner;
import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.Enumeration.eAntennaNo;
import com.clou.uhf.G3Lib.Enumeration.eReadType;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.ClouInterface.ISearchDevice;
import com.clou.uhf.G3Lib.Models.GPI_Model;
import com.clou.uhf.G3Lib.Models.Tag_Model;
import com.jamierf.rxtx.RXTXLoader;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import java.io.IOException;
import java.time.Clock;
import static java.util.Arrays.asList;
import java.util.Iterator;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;






import com.mongodb.client.model.Projections;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleCode implements IAsynchronousMessage, ISearchDevice  {
    static String ConnID = "";
      public static void main(String[] args)throws FileNotFoundException, IOException  
    {try
        {RXTXLoader.load();}
        catch(Exception ex){}
        Scanner sc = new Scanner(System.in);
        String readKey;
        SampleCode example = new SampleCode();
        while(true)
        {System.out.println("Please input TCP ConnID,Format: 'IP Address':'Connect Port' like \"192.168.1.116:9090\" \n");
    	ConnID = sc.next();
    	if(CLReader.CreateTcpConn(ConnID, example))
    			{System.out.println("Connect success!\n");
    				break;}
    			else
    			{System.out.println("Connect failure!\n");
    				continue;}}
            try{
                while (ConnID.equals(""))
                {Thread.sleep(3000);}
                while(true)
                {System.out.println("Current ConnID : " + ConnID + "\n");                  
                 System.out.println("6C Tag");
                 while (true)
                        {System.out.println("Current ConnID : " + ConnID + "\n");
                         System.out.println("Reading 6C Tag...");
                         while (true)
                                {System.out.println("executing selected method...");
                                  int antNum = 0;
                                  while (true){
                                         System.out.println("setting antenna :");
                                         antNum += eAntennaNo._1.GetNum();
                                         antNum += eAntennaNo._2.GetNum();
                                        eReadType readType;
                                        System.out.println("selecting ReadType...:"); 
                                        readType = eReadType.Single;
                                        CLReader._Config.Stop(ConnID);
                                        if (CLReader._Tag6C.GetEPC_TID(ConnID, antNum, readType) == 0)
                                        {System.out.println("Success!");   
                                        }}}}}}
            catch(Exception ex){
                 System.out.println(ex.getMessage());}
} 


// configuration pour la lecture des tags 

        @Override
        public void DebugMsg(String arg0) {
        // TODO Auto-generated method stub

    }

   

    @Override
    public void DeviceInfo(com.clou.uhf.G3Lib.Models.Device_Model arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void GPIControlMsg(GPI_Model gpi_model) {
        // TODO Auto-generated method stub

    }

    @Override
    public void OutPutTags(Tag_Model arg0) {
// Double NewPoids=99.0;

// Affichage dans la console
       System.out.println("EPC��" + arg0._EPC + " TID��" + arg0._TID );

      

// Connecter la db


ConnectionString cs = new ConnectionString("mongodb+srv://NewUser:newusermongodb@cluster0.c5kam.mongodb.net/?retryWrites=true&w=majority");
MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(cs).build();
MongoClient mongoClient = MongoClients.create(settings);
MongoDatabase db = mongoClient.getDatabase("RfidDb");
MongoCollection<Document> users = db.getCollection("RfidCollection");

////////////---------------------------------------------------------------------------------------------------------
     //Récupérer le Poids
 String poids = null;
            // using the Runtime exec method:
try{
            Process  p = Runtime.getRuntime().exec("python C:\\Users\\DELL\\Documents\\NetBeansProjects\\JavaApplication1\\src\\numbers.py");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            poids = stdInput.readLine();   } 
catch (IOException ex) {     
            Logger.getLogger(SampleCode.class.getName()).log(Level.SEVERE, null, ex);
        }
            Double NewPoids= Double.parseDouble(poids);
            System.out.println("Poids "+NewPoids);            
 // vérification si yá un document existant avec comme parametre le TID récupérer
   
          long count = users.count(new BsonDocument("TID", new BsonString(arg0._TID)));
    if (count > 0){
           
           System.out.println(" RESULT IS FOUND ");
//récuperer tout les poids du document avec un TID spécifique
  FindIterable<Document> selectDoc  = users.find(eq("TID", arg0._TID)).projection(fields(include("Poids_data.Poids"), excludeId()));
               MongoCursor< Document> sel  = selectDoc.iterator();
    while( sel.hasNext()) {

		Document docs1 = sel.next(); //return the poids-data doc with poids berk
                List<Document > listPoids = (List<Document >)docs1.get("Poids_data");//doc list with poids seulement
                System.out.println("poids list "+listPoids);
                Double  LastPoids =  (Double) listPoids.get(listPoids.size()-1).get("Poids");//the last poids in doc list  
                System.out.println("Last poids Json "+LastPoids);
// Traitement de données
    Double soustraction = NewPoids -LastPoids  ; 
    System.out.println(soustraction);
    if(soustraction> 0)
    {try {
Process  prelais1 = Runtime.getRuntime().exec("python relais1.py");
         }catch(IOException e){
e.printStackTrace();
}}
    else if (soustraction< 0) 
    {try {
Process  prelais2 = Runtime.getRuntime().exec("python relais2.py");
         }catch(IOException e){
e.printStackTrace();
}}         
}//------- fin while
           Bson filter = Filters.eq("TID",arg0._TID);
           BasicDBObject Poids_Data1 = new  BasicDBObject("Poids", NewPoids).append("Date", Clock.systemUTC().instant());
           Bson update = Updates.push("Poids_data",Poids_Data1);
           FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
           Document result = users.findOneAndUpdate(filter, update, options);
                 }
    else{
           System.out.println(" RESULT IS NOT FOUND ");
           String[][] data = {{ arg0._TID, arg0._EPC}};
           addCollection(data, users);
    }

}
// affichage de tid et epc, et tout le reste 

//Print entire collection
	private static void printCollection(MongoCollection<Document> coll) {
		FindIterable<Document> documents = coll.find();
		Iterator<Document> it = documents.iterator();
		while(it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc.toJson());
                     System.out.println("sayééééééee");
		}
	}
	//Adding to a collection

	private static void addCollection(String[][] team, MongoCollection<Document> coll) {
		for(String[] member : team) {
			Document memberDoc = makeDocument(member);
			coll.insertOne(memberDoc);
		}	
	}
	private static Document makeDocument(String[] member) {
Double poids = 50.3;
   List<Document> Poids_Data;
       Poids_Data = asList(new Document("Poids", poids).append("Date", Clock.systemUTC().instant()));
		return new Document("TID", member[0]).append("EPC", member[1]).append("Poids_data", Poids_Data);
	}

       //Clear collection
	private static void clearCollection(MongoCollection<Document> coll) {
		coll.deleteMany(new Document());
	}

    @Override
    public void OutPutTagsOver() {
        // TODO Auto-generated method stub
        System.out.println("OutPutTagsOver");
    }

    @Override
    public void PortClosing(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void PortConnecting(String connID) {
        // TODO Auto-generated method stub
        System.out.println(connID);
        if (CLReader.GetServerStartUp()) {
            System.out.println("A reader connected to this server: " + connID);
            ConnID = connID;
        }
    }

    @Override
    public void WriteDebugMsg(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void WriteLog(String arg0) {
        // TODO Auto-generated method stub
    }
}
