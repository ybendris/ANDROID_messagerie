package com.example.chat_2022_eleves.api;

import com.example.chat_2022_eleves.object.Authentification;
import com.example.chat_2022_eleves.object.ListConversations;
import com.example.chat_2022_eleves.object.ListMessages;
import com.example.chat_2022_eleves.object.Message;
import com.example.chat_2022_eleves.object.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
        /**
         * Authentification et récupération du hash
         * @param user
         * @param password
         * @return
         */
        @POST("authenticate")
        Call<Authentification> doAuthenticate(@Query("user") String user, @Query("password") String password);

        /**
         * Changer mot de passe
         * @param hash
         * @param password
         * @return
         */
        @PUT("users")
        Call<User> doNewMDP(@Header("hash") String hash, @Query("password") String password);

        /**
         * Liste les conversations
         * @param hash
         * @return
         */
        @GET("conversations")
        Call<ListConversations> doGetListConversation(@Header("hash") String hash);

        /**
         * Affiche les messages des utilisateurs non blacklistés de la conversation dont l'id est fourni
         * @param hash
         * @param convId
         * @return
         */
        @GET("conversations/{id}/messages")
        Call<ListMessages> doGetListMessage(@Header("hash") String hash, @Path("id") int convId);


        @POST("conversations/{id}/messages")
        Call<Message> doPostMessage(@Header("hash") String hash, @Path("id") int convId, @Query("contenu") String contenu);
}
