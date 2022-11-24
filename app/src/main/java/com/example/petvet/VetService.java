package com.example.petvet;
public class VetService {
    private String serviceID;
        private String vetID;
        private String serviceName;
        private String category;
        public VetService(){
        }
        public VetService(String serviceID,String vetID, String serviceName, String category){
            this.serviceID = serviceID;
            this.vetID = vetID;
            this.serviceName = serviceName;
            this.category =category;
        }
    public String getServiceID() {return serviceID;}
        public String getVetID(){
            return vetID;
        }
        public String getServiceName() {
            return serviceName;
        }
        public String getCategory(){return  category;}


    public void setServiceID(String serviceID) {this.serviceID = serviceID;}
    public void setServiceName(String serviceName){
            this.serviceName = serviceName;
        }
        public void setVetID(String vetID){
            this.vetID = vetID;
        }
        public void setCategory(String category){
            this.category = category;
        }

}

