package com.example.usercollageapplication.admin.faculty;

public class TeacherData {
        private String name,email,post,mobile,image,key;

        public TeacherData() {
        }

        public TeacherData(String name, String email, String post, String mobile, String image, String key) {
            this.name = name;
            this.email = email;
            this.post = post;
            this.mobile = mobile;
            this.image = image;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPost() {
            return post;
        }

        public String getMobile() {
            return mobile;
        }

        public String getImage() {
            return image;
        }

        public String getKey() {
            return key;
        }

}
