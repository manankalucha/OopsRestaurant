package in.dete.oops;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("payment/paytm/generate_checksum")
    Call<JsonObject> genCheckSum(@Body JsonObject obj);

    @GET("ride/cancel/{ride_id}")
    Call<JsonObject> cancelAcceptedRide(@Path("ride_id") String rideId);
}
