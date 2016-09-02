package com.example.news;

import java.util.List;

/**
 * Created by chen on 16-8-27.
 */
public class NewsContentBean {

    private int error_code ;
    private String message;
    private Data data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String index, subject, content, newscome, gonggao, sheying, shengao;
        private int visitcount;
        private List<Comments> comments;

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNewscome() {
            return newscome;
        }

        public void setNewscome(String newscome) {
            this.newscome = newscome;
        }

        public String getGonggao() {
            return gonggao;
        }

        public void setGonggao(String gonggao) {
            this.gonggao = gonggao;
        }

        public String getSheying() {
            return sheying;
        }

        public void setSheying(String sheying) {
            this.sheying = sheying;
        }

        public String getShengao() {
            return shengao;
        }

        public void setShengao(String shengao) {
            this.shengao = shengao;
        }

        public List<Comments> getComments() {
            return comments;
        }

        public void setComments(List<Comments> comments) {
            this.comments = comments;
        }

        public static class Comments{

            private int nid, cid;
            private String ccontent, cuser, ctime;

            public String getCtie() {
                return ctime;
            }

            public void setCtie(String ctie) {
                this.ctime = ctie;
            }

            public String getCuser() {
                return cuser;
            }

            public void setCuser(String cuser) {
                this.cuser = cuser;
            }

            public String getCcontent() {
                return ccontent;
            }

            public void setCcontent(String ccontent) {
                this.ccontent = ccontent;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getNid() {
                return nid;
            }

            public void setNid(int nid) {
                this.nid = nid;
            }
        }


    }

}
