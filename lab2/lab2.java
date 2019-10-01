import java.util.*;

class Merchant implements info{
    private String Name;
    private String Address;
    private float contribution;
    private Item[] items;
    private int item_index;
    private float reward;
    Merchant(String name,String address){
        this.Name=name;
        this.Address=address;
        this.contribution=0;
        this.items = new Item[10];
        this.item_index=0;
        this.reward=0;
    }
    void display(){
        System.out.println(this.Name+" "+this.Address+" "+this.contribution);
    }
    int add_item(int id,String name, float price,String category,int quantity,int offer){
        this.items[this.item_index]= new Item(id, name, price, category, quantity, offer);
        if(this.items[this.item_index]==null){
            return 0;
        }
        else{
            this.item_index+=1;
            return 1;
        }
    }
    String get_name(){
        return this.Name;
    }
    int display_item(int i){
        if(items[i]==null){
            System.out.println("Item Not found.");   
            return 0;
        }
        else{
            this.items[i].display();
            return 1;
        }
    }
    void edit_item(int id,float price,int quantity){
        if(items[id]==null){
            System.out.println("Item Not found.");
        }
        else{
            this.items[id].edit(price, quantity);
        }
    }
    void add_offer(int index,int offer){
        this.items[index].update_offer(offer);
    }
    float get_reward(){
        return this.reward;
    }
    Item return_item(){
        return this.items[this.item_index-1];
    }

    @Override
    public void give_info(){

        System.out.println(this.Name+" "+this.Address+" "+this.contribution);
    }
}
class Customer implements info{
    private String Name;
    private String Address;
    private int no_of_orders;
    private float reward;
    private float balance;
    private Transaction[] transactions;
    private int transaction_index;
    Customer(String name,String address){
        this.Name=name;
        this.Address=address;
        this.no_of_orders=0;
        this.reward=0;
        this.balance=100;
        this.transaction_index=0;
        this.transactions= new Transaction[10];
    }
    String get_name(){
        return this.Name;
    }
    void display(){
        System.out.println(this.Name+" "+this.Address+" "+this.no_of_orders);
    }
    void get_reward(){
        System.out.println(this.reward);
    }
    int make_transaction(float ammount,Item item,int quantity,Merchant merchant){
        if(ammount<=this.balance){
            transactions[transaction_index]=new Transaction(ammount, item, quantity, merchant);
            transaction_index+=1;
            this.balance-=ammount;
            return 1;
        }
        else if(ammount>this.balance && ammount<=this.balance+this.reward){
            transactions[transaction_index]=new Transaction(ammount, item, quantity, merchant);
            transaction_index+=1;
            this.reward-=(ammount-this.balance);
            this.balance=0;
            return 1;
        }
        else{
            return 0;
        }
    }
    void show_transactions(){
        for(int i=0;i<this.transaction_index;i++){
            transactions[i].display();
        }
    }

    @Override
    public void give_info(){

        System.out.println(this.Name+" "+this.Address+" "+this.no_of_orders);
    }
}
class Company{
    private float balance;
    Company(){
        this.balance=0;
    }
    void show_balance(){
        System.out.println(this.balance);
    }
    void add_transaction(float ammount){
        this.balance+=ammount;
    }
}
class Category{
    private String name;
    private Item[] items;
    private int item_index;
    private Merchant merchant;
    Category(String name,Merchant merchant){
        this.items = new Item[100];
        this.item_index=0;
        this.name=name;
        this.merchant=merchant;
    }
    Merchant get_merchant(){
        return this.merchant;
    }
    String get_name(){
        return this.name;
    }
    void add(Item item){
        this.items[this.item_index]=item;
        this.item_index+=1;
    }
    void show_items(){
        System.out.println(this.item_index);
        for(int i=0;i<this.item_index;i++){
            System.out.println(Integer.toString(i+1)+") "+this.items[i].get_name());
        } 
    }
    Item[] get_items(){
        return this.items;
    }
    int get_item_index(){
        return this.item_index;
    }
    Item select_item(int i){
        return items[i];
    }
}
class Cart{
    private Item[] items;
    private int item_index;
    Cart(){
        this.items=new Item[10];
        this.item_index=0;
    }
    void add(Item item){
        this.items[this.item_index]=item;
        this.item_index+=1;
    }
    float total_ammount(){
        float ammount=0;
        for(int i=0;i<item_index;i++){
            ammount+=items[i].get_price();
        }
        return ammount;
    }
}
class Item{
    private int id;
    private String name;
    private float price;
    private String category;
    private int quantity;
    private int offer;
    Item(int id,String name, float price,String category,int quantity,int offer) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.category=category;
        this.quantity=quantity;
        this.offer=offer;
    }
    void reduce_quantity(int quantity){
        this.quantity-=quantity;
    }
    void edit(float price,int quantity){
        this.price=price;
        this.quantity=quantity;
    }
    float get_price(){
        if(this.offer==2){
            return (float)(this.price*0.75);
        }
        else{
            return this.price;
        }
    }
    String get_name(){
        return this.name;
    }
    void display(){
        System.out.print(this.id+" "+this.name+" "+this.price+" "+this.category+" "+this.quantity);
        if(this.offer==1){
            System.out.println(" Buy One Get One");
        }
        else if(this.offer==2){
            System.out.println(" 25% OFF");
        }
        else{
            System.out.println(" None");
        }
    }
    void update_offer(int offer){
        this.offer=offer;
    }
}
class Transaction{
    private float ammount;
    private Item item;
    private int quantity;
    private Merchant merchant;
    Transaction(float ammount,Item item,int quantity,Merchant merchant){
        this.ammount=ammount;
        this.item=item;
        this.quantity=quantity;
        this.merchant=merchant;
    }
    void display(){
        System.out.println("Bought item "+this.item.get_name()+" quantity : "+this.quantity+"for Rs."+this.item.get_price()+"from merchant "+this.merchant.get_name());
    }
}

interface info{
    void give_info();
}

class lab2{
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        
        Merchant[] merchants = new Merchant[5];
        Customer[] customers = new Customer[5];
        Category[] categories = new Category[100];
        Company company = new Company();
        int category_index=0;

        merchants[1]=new Merchant("Kartikaye", "Britain");
        merchants[2]=new Merchant("Akshita", "India");
        merchants[3]=new Merchant("Satyam", "USA");
        merchants[4]=new Merchant("Animesh", "Caribbean");
        merchants[0]=new Merchant("Shefali", "Austrailia");

        customers[0]=new Customer("Ritik","Haryana");
        customers[1]=new Customer("Kunal","Punjab");
        customers[2]=new Customer("Yash","U.P");
        customers[3]=new Customer("Avinash","Assam");
        customers[4]=new Customer("Anjali","Delhi");
        
        int item_id=0;

        int flag_1=1;
        while(flag_1!=0){
            System.out.println("Welcome to Mercury");
            String[] main_menu={"Enter as Merchant",
            "Enter as Customer","See user detail","Company account balance","Exit"};
            display(main_menu);
            int n = s.nextInt();
            if(n==1){
                System.out.println("Choose Merchant");
                for(int i=0;i<5;i++){
                    System.out.print(Integer.toString(i+1)+" "+merchants[i].get_name()+"\n");
                }
                int m=s.nextInt();

                Merchant merchant = merchants[m-1];

                System.out.println("Welcome "+merchant.get_name());

                int flag_2=1;
                while(flag_2!=0){
                    String[] merchant_menu={"Add item","Edit Item","Search","Add Offers","Reward","Exit"};
                    display(merchant_menu);
                    int n_2=s.nextInt();
                    s.nextLine();
                    if(n_2==1){
                        System.out.print("Name : ");
                        String name = s.nextLine();
                        System.out.print("Price : ");
                        float price = s.nextFloat();
                        System.out.print("Quantity : ");
                        int quantity = s.nextInt();
                        s.nextLine();
                        System.out.print("Category : ");
                        String category = s.nextLine();
                        // System.out.print("Offer (0. None, 1. Buy One Get One, 2. 25% off) : ");
                        // int offer = s.nextInt();
                        int is_added=merchant.add_item(item_id, name, price, category, quantity, 0);
                        if(is_added==1){
                            merchant.display_item(item_id);
                            item_id+=1;
                            int cat_match=0;
                            for(int i=0;i<category_index;i++){
                                // System.out.println(i);
                                if(categories[i]!=null && categories[i].get_name().toLowerCase()==category.toLowerCase()){
                                    cat_match=1;
                                    categories[i].add(merchant.return_item());
                                    System.out.println(categories[i].get_name());
                                }
                            }
                            if(cat_match==0){
                                categories[category_index]=new Category(category,merchant);
                                categories[category_index].add(merchant.return_item());
                                // System.out.println(categories[category_index].get_name());
                                category_index+=1;
                            }
                            // System.out.println("cat_match"+cat_match);
                        }
                        else{
                            System.out.println("Error adding Item");
                        }
                    }
                    else if(n_2==2){
                        System.out.print("Enter item code : ");
                        int item_code=s.nextInt();
                        int available=merchant.display_item(item_code);
                        if(available==1){
                            System.out.print("Enter new Price : ");
                            float price=s.nextFloat();
                            System.out.print("Enter new Quantity : ");
                            int quantity=s.nextInt();
                            s.nextLine();
                            merchant.edit_item(item_code, price, quantity);
                            merchant.display_item(item_code);
                        }
                    }
                    else if(n_2==3){
                        System.out.println("Choose Category");
                        for(int i=0;i<category_index;i++){
                            System.out.println(Integer.toString(i+1)+") "+categories[i].get_name());
                        }
                        int n_6 = s.nextInt();
                        for(int i=0;i<categories[n_6-1].get_item_index();i++){
                            categories[n_6-1].get_items()[i].display();
                        }
                    }
                    else if(n_2==4){
                        System.out.print("Enter item code : ");
                        int item_code=s.nextInt();
                        int available=merchant.display_item(item_code);
                        if(available==1){
                        System.out.println("Enter Offer:\n1. Buy One Get One Free\n2. 25% OFF");
                        int offer=s.nextInt();
                        merchant.add_offer(item_code,offer);
                        merchant.display_item(item_code);
                        }
                    }
                    else if(n_2==5){
                        System.out.print("Total Reward : ");
                        System.out.println(merchant.get_reward());
                    }
                    else if(n_2==6){
                        flag_2=0;
                    }
                    else{
                        System.out.println("Wrong Choice");
                    }
                }
            }
            else if(n==2){
                System.out.println("Choose Customer");
                for(int i=0;i<5;i++){
                    System.out.print(Integer.toString(i+1)+" "+customers[i].get_name()+"\n");
                }
                int c=s.nextInt();
                Customer customer = customers[c-1];
                Cart cart= new Cart();
                System.out.println("Welcome "+customer.get_name());

                int flag_3=1;

                while(flag_3!=0){

                    String[] customer_menu={"Search","Checkout","Reward","Recent Orders","Exit"};
                    display(customer_menu);

                    int n_3=s.nextInt();
                    if(n_3==1){
                        System.out.println("Choose Category");
                        for(int i=0;i<category_index;i++){
                            System.out.println(Integer.toString(i+1)+") "+categories[i].get_name());
                        }
                        int n_4 = s.nextInt();

                        System.out.println("Choose item");

                        categories[n_4-1].show_items();
                        
                        int n_5 = s.nextInt();
                        Item item = categories[n_4-1].get_items()[n_5-1];
                        System.out.print("You selected : ");
                        item.display();


                        System.out.println("Enter Quantity : ");
                        int quantity= s.nextInt();

                        String[] buy_options={"Buy Item","Add to cart","Exit"};
                        display(buy_options);
                        int n_7=s.nextInt();

                        Merchant m = categories[n_4-1].get_merchant();

                        if(n_7==1){
                            float ammount=item.get_price()*quantity;
                            System.out.println("Your Total : "+ammount);
                            
                            if(customer.make_transaction(ammount,item,quantity,m)==1){
                                item.reduce_quantity(quantity);
                                System.out.println("Transaction Sucessfull.");
                            }
                            else{
                                System.out.println("Not Enough Balance");
                            }


                        }
                        else if(n_7==2){

                        }
                        else if(n_7==3){
                            
                        }
                        else{
                            System.out.println("Wrong Choice");
                        }

                    }
                    else if(n_3==2){
    
                    }
                    else if(n_3==3){
                        customer.get_reward();
                    }
                    else if(n_3==4){
                        customer.show_transactions();
                    }
                    else if(n_3==5){
                        flag_3=0;
                    }
                    else{
                        System.out.println("Wrong Choice");
                    }
                }
            }
            else if(n==3){
                System.out.print("Merchant(M) or Customer(C) : ");
                char n_3 = s.next().charAt(0);
                System.out.print("Enter ID : ");
                int n_4 = s.nextInt();
                if(n_3=='M'||n_3=='m'){

                    merchants[n_4].give_info();

                    //merchants[n_4].display();
                }
                else if(n_3=='C'||n_3=='c'){
                    customers[n_4].give_info();;
                }
                else{
                    System.out.println("Wrong Choice");
                }
                
            }
            else if(n==4){
                company.show_balance();;
            }
            else if(n==5){
                flag_1=0;
            }
            else{
                System.out.println("Wrong Choice");
            }
        } 

    }
    void assign(){
        
    }
    public static void display(String[] show){
        for(int i=0;i<show.length;i++){
            System.out.println(Integer.toString(i+1)+") "+show[i]);
        }
    }
}