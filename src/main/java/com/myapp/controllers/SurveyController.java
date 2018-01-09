package com.myapp.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sheng.wang on 2017/07/28.
 */
@RestController
@RequestMapping(value = "/survey")
@CrossOrigin
public class SurveyController {

    private final Map<String, String> nameIdMapping = new HashMap<>();
    private final Map<String, String> idContentMapping = new HashMap<>();


    @RequestMapping(value = "/save/{id}/{name}", method = RequestMethod.POST)
    public String saveSurvey(@PathVariable String id, @PathVariable String name, @RequestBody String content) {
        nameIdMapping.put(name, id);
        idContentMapping.put(id, content);
        return "ok";
    }

    @RequestMapping(value = "/search/{searchKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SurveyNameAndId> searchSurveys(@PathVariable  String searchKey) {
        return nameIdMapping.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(searchKey))
                .map(entry -> new SurveyNameAndId(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSurvey(@PathVariable String id) {
        return idContentMapping.get(id);
    }

    public final static class SurveyNameAndId {
        private final String name;
        private final String id;

        SurveyNameAndId(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}
