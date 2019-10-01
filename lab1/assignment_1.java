// Name : Ajay Prakash
// Branch : CSAM
// Roll No : 2018215
import java.util.*;
class Student{
    private float CGPA;
    private String Branch;
    private boolean Selected;
    private String company;
    private Score[] scores;
    private int score_index;
    Student(float cgpa, String branch){
        this.CGPA=cgpa;
        this.Branch=branch;
        this.Selected=false;
        this.company="";
        this.scores = new Score[100];
        this.score_index=0;
    }
    float get_CGPA(){
        return this.CGPA;
    }   
    String get_Branch(){
        return this.Branch;
    }
    boolean get_Selected(){
        return this.Selected;
    }
    String get_company(){
        return this.company;
    }
    void show_details(){
        String output="";
        output+=this.CGPA+" "+this.Branch+" ";
        if(this.Selected==true){
            output+="CLOSE ";
        }
        else{
            output+="OPEN ";
        }
        output+=this.company+" ";
        System.out.println(output);
    }
    void set_score(Score score){
        this.scores[this.score_index] = score;
        this.score_index+=1;
    }
    int get_score_index(){
        return this.score_index;
    }
    Score get_score(int i){
        return this.scores[i];
    }
    void selected(Company company){
        this.company=company.Name;
        this.Selected=true;
    }
}
class Company{
    public String Name;
    private String CourseCriteria[];
    private int no_of_students;
    private boolean ApplicationStatus;
    private Score[] scores;
    private int score_index;
    private Student[] students;
    private int students_index;
    Company(String name, String[] course_criteria, int no_of_students){
        this.Name=name;
        this.CourseCriteria=course_criteria;
        this.no_of_students=no_of_students;
        this.ApplicationStatus=true;
        this.scores = new Score[100];
        this.score_index=0;
        this.students = new Student[100];
        this.students_index=0;
    }
    String[] get_courses(){
        return this.CourseCriteria;
    }
    int get_students(){
        return this.no_of_students;
    }
    boolean get_status(){
        return this.ApplicationStatus;
    }
    String print_courses(){
        String[] courses = this.CourseCriteria;
        String output="";
        for(int i=0;i<courses.length;i++){
            output+=courses[i]+" ";
        }
        return output;
    }
    String print_status(){
        if(this.ApplicationStatus==true){
            return "OPEN";
        }
        return "CLOSE";
    }
    void set_score(Score score){
        this.scores[this.score_index] = score;
        this.score_index+=1;
    }
    Score[] get_scores(){
        return this.scores;
    }
    void selected(Student student){
        this.students[students_index]=student;
        this.students_index+=1;
        this.no_of_students-=1;
        if(this.no_of_students==0){
            this.ApplicationStatus=false;
        }
    }
    void print_scores(){
        for(int i=0;i<this.score_index;i++){
            System.out.print(this.scores[i].get_score()+" ");
        }
        System.out.println("");
    }
    void sort(){
        Score[] scores = this.scores;
        int len = this.score_index;
        for(int i=0;i<len;i++){
            int max_idx = i;
            for(int j= i+1;j<len-1 ;j++){
                if(scores[j].get_score()>scores[max_idx].get_score()){
                    max_idx = j;
                }
                if(scores[j].get_score()==scores[max_idx].get_score()){
                    if(scores[j].get_student().get_CGPA()>scores[max_idx].get_student().get_CGPA()){
                        max_idx = j;
                    }
                }
            }
            Score temp = scores[max_idx];
            scores[max_idx]=scores[i];
            scores[i]=temp;
        }
        this.scores = scores;
    }
}
class Score{
    private int score;
    private Company company;
    private Student student;
    Score(int score,Company company,Student student){
        this.score = score;
        this.company = company;
        this.student = student;
    }
    String print_score(){
        String output=Integer.toString(this.score)+" "+this.company.Name;
        return output;
    }
    int get_score(){
        return this.score;
    }
    Student get_student(){
        return this.student;
    }
}
class assignment_1{
    public static int unplaced_students(int n,Student[] students){
        int count = 0;
        for(int i=0;i<n;i++){
            if(students[i]!=null && students[i].get_Selected()==false){
                count++;
            }
        }
        return count;
    }
    public static void main(String[] args){
        Scanner s= new Scanner(System.in);
        
        System.out.print("Enter number of students : ");
        int n = Integer.parseInt(s.nextLine());
        
        Student students[] = new Student[n]; 
        
        for(int i=0;i<n;i++){
            float cgpa = s.nextFloat();
            String branch = s.nextLine().replace(" ", "").toUpperCase();
            students[i] = new Student(cgpa, branch);

        }
        // System.out.print("Enter number of queries : ");
        // int query = Integer.parseInt(s.nextLine());

        Company companies[] = new Company[100];
        int comapny_no=0;

        while(unplaced_students(n,students)>0){
            System.out.print("Query : ");
            int q=s.nextInt();
            if(q==1){
                s.nextLine();

                System.out.print("Enter Name of Company : ");
                String name = s.nextLine().toUpperCase();
                
                System.out.print("Number of Eligible Courses : ");
                int no_of_courses = Integer.parseInt(s.nextLine());

                String[] courses = new String[no_of_courses];
                for(int i=0;i<no_of_courses;i++){
                    System.out.print("Course : ");
                    courses[i]=s.nextLine().toUpperCase();
                }
                
                System.out.print("Number of Required Students : ");
                int no_of_students = Integer.parseInt(s.nextLine());
                companies[comapny_no] = new Company(name,courses,no_of_students);


                Company company=companies[comapny_no];
                System.out.println("Company : "+company.Name);
                System.out.println("Course Criteria : "+company.print_courses());
                System.out.println("No of Required Students : "+company.get_students());
                System.out.println("Status : "+company.print_status());

                System.out.println("Enter Scores of technical rounds.");

                for(int i=0;i<n;i++){
                    String branch = students[i].get_Branch();
                    for(int k=0;k<no_of_courses;k++){
                        if(companies[comapny_no].get_courses()[k].equals(branch)){
                            System.out.print("Enter Score for Roll No. " + Integer.toString(i+1) + " ");
                            int score = s.nextInt();
                            s.nextLine();
                            Score score_obj = new Score(score, companies[comapny_no] ,students[i]);
                            students[i].set_score(score_obj);
                            companies[comapny_no].set_score(score_obj);
                        }
                    }
                }
                companies[comapny_no].sort();
                // companies[comapny_no].print_scores();
                
                comapny_no+=1;
            }
            else if(q==2){
                s.nextLine();
                for(int i=0;i<n;i++){
                    if(students[i]!=null&&students[i].get_Selected()){
                        System.out.print(i+1);
                        System.out.print(" ");
                        students[i]=null;
                    }
                    System.out.println(" ");
                }
            }
            else if(q==3){
                s.nextLine();
                for(int i=0;i<comapny_no;i++){
                    if(companies[i]!=null&&companies[i].get_status()==false){
                        System.out.print(companies[i].Name+"    ");
                        companies[i]=null;
                    }
                }
                System.out.println(" ");
            }
            else if(q==4){
                s.nextLine();
                System.out.println(unplaced_students(n,students));
            }
            else if(q==5){
                s.nextLine();
                for(int i=0;i<comapny_no;i++){
                    if(companies[i]!=null && companies[i].get_status()==true){
                        System.out.print(companies[i].Name);
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }
            else if(q==6){
                System.out.print("Enter name of company : ");
                s.nextLine();
                String company_name = s.nextLine().toUpperCase();
                for(int i=0; i<comapny_no; i++){
                    if(company_name.equals(companies[i].Name)){
                        companies[i].sort();
                        if(unplaced_students(n,students)<companies[i].get_students()&&companies[i].get_students()>0){
                            for(int k=0;k<n;k++){
                                if(students[k]!=null && students[k].get_Selected()==false){
                                    students[k].selected(companies[i]);
                                    companies[i].selected(students[k]);
                                }
                            }
                        }
                        else{
                            Score[] scores = companies[i].get_scores();
                            for(int k=0;k<scores.length;k++){
                                if(companies[i].get_students()>0){
                                    scores[k].get_student().selected(companies[i]);
                                    companies[i].selected(scores[k].get_student());
                                }
                            }
                        }
                    }
                }
            }
            else if(q==7){
                System.out.print("Enter name of company : ");
                String company_name = s.nextLine().toUpperCase();
                for(int i=0; i<comapny_no; i++){
                    if(company_name.equals(companies[i].Name)){
                        Company company=companies[comapny_no];
                        System.out.println("Company : "+company.Name);
                        System.out.println("Course Criteria : "+company.print_courses());
                        System.out.println("No of Required Students : "+company.get_students());
                        System.out.println("Status : "+company.print_status());
                    }
                }
            }
            else if(q==8){
                System.out.print("Enter Roll no of student : ");
                int roll_no = s.nextInt() - 1;
                s.nextLine();
                if(students[roll_no]!=null){
                    students[roll_no].show_details();
                }
            }
            else if(q==9){
                System.out.print("Enter Roll no of student : ");
                int roll_no = s.nextInt();
                s.nextLine();
                Student student = students[roll_no-1];
                int score_index = student.get_score_index();
                for(int i=0 ; i<score_index;i++){
                    System.out.println(student.get_score(i).print_score()+" ");
                }
            }
            
        }
    }
}