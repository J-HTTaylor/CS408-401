package com.example.application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component 
public class CanvasAPiCalls {
    //gets relevent apis, urls and a HTTP client to handle requests and deal with JSON formatting
    @Value("${CANVAS_API_TOKEN:}")
    private String token;
    @Value("${CANVAS_BASE_URL:}")
    private String url ;
    private HttpClient canvasClient = HttpClient.newBuilder().build();
    private HttpResponse<String> canvasResponse;
    private HttpRequest canvasRequest;
    private ObjectMapper mapper = new ObjectMapper();

    /***gets list of all course currently taking
     * @throws Exception when Http status code is invalid or the url/token is empty
     * @returns a list of all the values that matches the given param
     * */

    public List<String> getCourses() throws Exception{
        List<String> coursesTaking = new  ArrayList<>();

        try{
            canvasRequest = HttpRequest.newBuilder().uri(URI.create(url + "/api/v1/users/self/courses?enrollment_state=active")).header("Authorization", "Bearer " + token).build();
            canvasResponse = canvasClient.send(canvasRequest, HttpResponse.BodyHandlers.ofString());
            
            //checks or valid response
            if(canvasResponse.statusCode() >= 400){
                System.err.println("Http resposned with unexpected status code. Expected 200 - 205. Recieved = " + canvasResponse.statusCode());
                throw new IllegalStateException();
            }

            JsonNode baseNode = mapper.readTree(canvasResponse.body());

            //adds all course names to list and checks the url/ token is valid
            for(JsonNode currrentNode : baseNode){
                coursesTaking.add(currrentNode.get("name").asString());
            }
        }
        catch(Exception e){
            System.out.println(e);
            if( token == null || token == "" || url == null || url == ""){
                System.out.println(" token is : " + token + " when valid token was expected");
                System.out.println(" url is : " + url + "/api/v1/users/self/courses?enrollment_state=active, when valid token was expected");
            }
        }
        
        return coursesTaking;
    }


    //gets list of all assignments for a given course
    public List<String[]> getAssignments(String courseName){
        List<String[]> assignments = new ArrayList<>();
        try{
            canvasRequest = HttpRequest.newBuilder().uri(URI.create(url + "/api/v1/users/self/upcoming_events")).header("Authorization", "Bearer " + token).build();
            canvasResponse = canvasClient.send(canvasRequest, HttpResponse.BodyHandlers.ofString());
            
            //checks or valid response
            if(canvasResponse.statusCode() >= 400){
                System.err.println("Http resposned with unexpected status code. Expected 200 - 205. Recieved = " + canvasResponse.statusCode());
                throw new IllegalStateException();
            }

            JsonNode baseNode = mapper.readTree(canvasResponse.body());

            //adds all assignments upcoming for the week for a given course 
            for(JsonNode currrentNode : baseNode){
                if(currrentNode.get("context_name").asString().equals(courseName)){   
                    assignments.add(new String[]{currrentNode.get("title").asString(), currrentNode.get("all_day_date").asString(),currrentNode.get("context_name").asString(), currrentNode.get("html_url").asString()});
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
            if( token == null || token == "" || url == null || url == ""){
                System.out.println(" token is : " + token + " when valid token was expected");
                System.out.println(" url is : " + url + "/api/v1/users/self/courses?enrollment_state=active, when valid token was expected");
            }
        }
        return  assignments;
    }
    }

