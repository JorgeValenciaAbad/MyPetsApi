package it.jorge.protectora.Controller;

import it.jorge.protectora.Model.BaseResponse;

public class ResponseController {

    public static BaseResponse Ok = new BaseResponse("Successful", 0);
    public static BaseResponse ERROR1 = new BaseResponse("No accept request", 1);
    public static BaseResponse ERROR2 = new BaseResponse("Phone is incorrect", 2);
    public static BaseResponse ERROR3 = new BaseResponse("Identification is incorrect", 3);
    public static BaseResponse ERROR4 = new BaseResponse( "Born Date is incorrect", 4);
    public static BaseResponse ERROR5 = new BaseResponse( "The operation cannot be performed, please try again in the next trade.", 5);
    public static BaseResponse ERROR6 = new BaseResponse( "Unauthorized", 6);

}
