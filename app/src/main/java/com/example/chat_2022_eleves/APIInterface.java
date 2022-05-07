package com.example.chat_2022_eleves;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

        @GET("conversations")
        Call<ListConversations> doGetListConversation(@Header("hash") String hash);

        @GET("conversations/{id}/messages")
        Call<ListMessages> doGetListMessage(@Header("hash") String hash, @Path("id") int convId);

        /*@POST("conversations/{id}/messages?")
        Call<Message> doSetListMessage(@Header("hash") String hash, @Path("id") int convId, @Query("contenu") String contenu);*/
}
