package shortvideo.stream;

import com.google.gson.Gson;

public class StreamProducerExe {

	public static void main(String[] args) {
		System.out.println("producer");
		StreamProducer producer = new StreamProducer();
		
         
		Customer cus = new Customer();
		cus.setName("Vitor");
		cus.setAge(19);
		cus.setCpf("1234567890");

		Gson gson = new Gson();
		String json = gson.toJson(cus);
		System.out.println(json);
	
	
		producer.produce(json);
	
	
	}	

	

}
