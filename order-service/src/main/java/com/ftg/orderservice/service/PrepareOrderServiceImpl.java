package com.ftg.orderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrepareOrderServiceImpl {

	private final Logger log = LoggerFactory.getLogger(PrepareOrderServiceImpl.class);
	
	 public boolean makePayment(String amount, String orderId) {
	        log.info(" Transferring to FoodDelivery Global AC of Amount : {} from customer acc by payment Provider for Order Id :  " + orderId, amount);
	        return true;
	        //return false;
	    }

	    public boolean acknowledgeCustomer(String orderId) {
	        log.info(" Payment Done for $ 10 and your order is placed order Id : " + orderId);
	        return true;
	        //return false;
	    }

	    public boolean notifyRestuarantAndFetchAck(String food, String orderId, String restaurantId) {
	      log.info("Notifying Restaurant with Id : {} on Food : {} for OrderId : " + orderId, restaurantId, food);
	        return true;
	        //return false;
	    }

	    public boolean sendPaymentToRestaurant(String amount, String orderId, String restaurantId) {
	        log.info("Sending Amount : {}  to restaurant : {} for the Food with orderId : " + orderId, amount, restaurantId);
	        return true;
	        //return false;
	    }

	    public String searchAndAssignDeliveryPartner(String orderId, String restaurantId) {
	        log.info("Searching Delivery Genie near to restaurant : {} for the Food Delivering with orderId : " + orderId);
	       return "ABC_DEL_GENIE_ID";

	        // Failure Scenario roll back above transactions
	        //return null;

	    }

	    public boolean revert(String amount, String orderId) {
	        log.info(" Reverting Transferred to FoodDelivery Global AC of Amount : {} from customer acc by payment Provider for Order Id :  " + orderId, amount);
	        return true;
	    }

	    public boolean acknowledgeCustomerForFailure(String orderId) {
	        log.info(" Reverted your payment for $ 10 for your order due to technical Issues : " + orderId);
	        return true;
	    }

	    public boolean notifyRestuarantAndSendFailAck(String food, String orderId, String restaurantId) {
	        log.info("Notifying Failure that customer has cancelled the order Restaurant with Id : {} on Food : {} for OrderId : " + orderId, restaurantId, food);
	        return true;
	    }

	    public boolean revertPaymentToRestaurant(String amount, String orderId, String restaurantId) {
	        log.info("Reverting Amount : {}  to restaurant : {} for the Food with orderId : " + orderId, amount, restaurantId);
	        return true;
	    }

	    public String noDeliveryPersonsFound(String orderId, String restaurantId) {
	        log.info("No Delivery Genie near to restaurant : {} for the Food Delivering with orderId : " + orderId);
	        return "FAILURE";
	    }
}
