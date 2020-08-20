package com.kwang.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.kwang.dto.BasicResponse;
import com.kwang.jwt.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

class Entry{
    int userid;
    int entryid;

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEntryid() {
        return this.entryid;
    }

    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    public Entry(int userid, int entryid) {
        this.userid = userid;
        this.entryid = entryid;
    }

}


@CrossOrigin
// (origins = { "http://localhost:3000",
// "http://i3a511.p.ssafy.io" })
// http://i3a511.p.ssafy.io/api/account/join

@RestController
@Controller
public class WaitController {
    //                  uid        entryId
    static Queue <Entry> youtubeWaitQueue = new LinkedList<Entry>();
    static Queue <Entry> translateWaitQueue = new LinkedList<Entry>();
    
    //              entryId
    static HashSet <Integer> youtubeWait = new HashSet <Integer>();
    static HashSet <Integer> translateWait = new HashSet <Integer>();

    static int entryId;

    @Autowired
    JwtService jwtService;
    /*
    init : 초기 입장
    out : 퇴장
    ask : 대기확인
    */
    @GetMapping("/api/wait/youtube")
	@ApiOperation(value = "youtube 대기")
	public Object youtubeWait(@RequestParam(required = true) final String enter, HttpServletRequest req) {
        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
        
        int userid = (int) (long) jwtService.getUserInfo(req).get("userid");
        
        if(enter.equals("init")){
            if(youtubeWaitQueue.size() == 0){
                result.status = true;
                result.data = "바로 입장 가능합니다.";
                result.object = true;
                response = new ResponseEntity<>(result, HttpStatus.OK);
                return response;
            } else {
                entryId++;
                youtubeWaitQueue.offer(new Entry(userid, entryId));
                youtubeWait.add(entryId);
                result.status = false;
                result.data = youtubeWaitQueue.size() + "명의 대기자가 있습니다.";
                result.object = false;
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }
        } 
        return response;
	}
}