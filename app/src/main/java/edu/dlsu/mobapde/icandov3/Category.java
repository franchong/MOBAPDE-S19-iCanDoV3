package edu.dlsu.mobapde.icandov3;

/**
 * Created by Dell on 12/08/2017.
 */

    public class Category {

        public static final String TABLE_NAME = "category";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";

        private long id;
        private String name;
        private int numTasks;

        public Category() {}
        public Category(String name) {
       this.name = name;
        }


    public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumTasks() {
            return numTasks;
        }

        public void setNumTasks(int numTasks) {
            this.numTasks = numTasks;
        }
}
