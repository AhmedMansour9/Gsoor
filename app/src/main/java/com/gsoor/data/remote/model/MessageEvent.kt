package com.gsoor.data.remote.model

 class MessageEvent {
     var Message:String = ""
      var cat_Id:String?= String()
      var brand_Id:String?= String()
      var min_Price:String?= String()
      var max_Price:String?= String()

     constructor(Message: String) {
        this.Message = Message
    }



}