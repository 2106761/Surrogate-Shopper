package com.example.test102;
//To keep the order we will create a class that will contain all the request attributes
public class RequestClass {

        private int id;
        private String name;
        private String location;
        private String list;

        public RequestClass(int id, String name, String location, String list) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.list = list;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getList() {
            return list;
        }
    }

