package com.example.petvet;
public class OrderDetails {
    private String clientRequestId;
    private String clientId;
        private String orderDate;
        private String vetUserId;
        public OrderDetails(){
        }
        public OrderDetails(String clientRequestId, String clientId, String orderDate,String vetUserId){
            this.clientRequestId = clientRequestId;
            this.clientId = clientId;
            this.orderDate = orderDate;
            this.vetUserId = vetUserId;
        }
        //getters
        public String getClientRequestId() {return clientRequestId;}
        public String getClientId(){
            return clientId;
        }
        public String getOrderDate(){return orderDate;}
        public String getVetUserId() {return vetUserId;}
    //setters
        public void setClientRequestId(String clientRequestId) {this.clientRequestId = clientRequestId;}
        public void setClientId(String clientId){
            this.clientId = clientId;
        }
        public void setOrderDate(String orderDate){
            this.orderDate = orderDate;
        }
    public void setVetUserId(String vetUserId) {this.vetUserId = vetUserId;}
}

