// Name : Ajay Prakash
// Roll No : 2018215
import java.util.*;
import java.util.Random;
class Game{
    private User[] users;
    private int user_index;
    Game(){
        this.user_index=0;
        this.users=new User[10];
    }
    void game_begin(){
        Scanner s = new Scanner(System.in);
        int flag=1;
        System.out.println("Welcome to ArchLegends");
        while(flag!=0){
            String outputs[]={"New User","Existing User","Exit"};
            Display display=new Display();
            display.show(outputs);
            int input=s.nextInt();
            if(input==1){
                s.nextLine();
                System.out.print("Enter Username : ");
                String name = s.nextLine();
                System.out.println("Choose a Hero");
                String warriors[]={"Warrior","Theif","Mage","Healer"};
                display.show(warriors);
                int h=s.nextInt();

                this.users[this.user_index]=new User(name,h);
                this.user_index+=1;

                System.out.println("User Created. You can now login.");
            }
            else if(input==2){
                s.nextLine();
                System.out.print("Enter Username : ");
                String name = s.nextLine();
                int check = check_user(name);
                if(check!=-1){
                    System.out.println("User Found... logging in");
                    System.out.println("Welcome "+users[check].get_name());
                    Heros hero= new Heros();
                    int h = users[check].get_hero();
                    if(h==1){
                        hero = new Warrior();
                    }
                    else if(h==2){
                        hero = new Theif();
                    }
                    else if(h==3){
                        hero = new Mage();
                    }
                    else if(h==4){
                        hero = new Healer();
                    }
                    else{
                        System.out.println("Wrong Choice");
                    }
                    int[] level_xp={20,60,100};
                    int level_index=0;
                    int level=1;
                    int monst_level=1;
                    Monster monster= new Monster();
                    while(monst_level!=4){
                        Random rand = new Random(); 

                        int rand1=rand.nextInt(8);
                        int rand2=rand.nextInt(8);
                        int rand3=rand.nextInt(8);
                        
                        System.out.println("Choose next location");
                        String levels[] ={Integer.toString(rand1),Integer.toString(rand2),Integer.toString(rand3)};
                        display.show(levels);

                        int in=s.nextInt();
                        int loc=1;
                        if(in==1)loc=rand1;
                        if(in==2)loc=rand2;
                        if(in==3)loc=rand3;
                        if(loc%3==1){
                            monster= new Goblins();
                            monst_level=1;
                        }
                        else if(loc%3==2){
                            monster= new Zombies();
                            monst_level=2;
                        }
                        else if(loc%3==0){
                            monster= new Fiends();
                            monst_level=3;
                        }
                        else{
                            monster= new Goblins();
                            monst_level=1;
                        }
                        if(level==4){
                            monster= new Lionfang();
                            monst_level=4;
                        }
                        // hero.set_HP(monst_level);
                        System.out.println("You are on Level "+level);
                        System.out.println("XP needed : "+level_xp[level_index]+" Current XP :"+hero.get_XP());
                        int win = game_start(monster,hero,level_xp[level_index],level);
                        if(win == 1){
                            // level++;
                            level+=1;
                            level_index++;
                            hero.level_up();
                        }
                    }
                }
                else{
                    System.out.println("User NOT Found.. Create User first.");
                }
            }
            else if(input==3){
                flag=0;
            }
            else{
                System.out.println("Wrong Input");
            }
            }
    }
    int game_start(Monster monster,Heros hero,int xp,int hero_level){
        System.out.println("Fight Started. You are fighting a level "+monster.get_level()+" Monster.");
        int count=0;
        int special_index=0;
        Scanner s = new Scanner(System.in);
        hero.set_HP(hero_level);
        while(monster.get_HP()!=0 || hero.get_HP()!=0){

            count++;

            hero.special(monster);
            hero.special_update();

            if(hero.get_HP()<=0){
                System.out.println("You Were Killed");
                System.out.println("Level Restarted");
                hero.set_HP(hero_level);
                monster.revive();
            }
            if(monster.get_HP()<=0){
                System.out.println("You Killed");
                int reward = hero.monst_kill(monster,hero_level);
                System.out.println(reward + " XP awarded");
            }
            if(hero.get_XP()>=xp){
                return 1;
            }

            System.out.println("Your HP: "+hero.get_HP()+" Monster HP: "+monster.get_HP());

            System.out.println("Choose Move:");
            System.out.println("1) Attack");
            System.out.println("2) Defense");
            if(count>4){
                System.out.println("3) Special Attack");
            }
            int move = s.nextInt();
            if(move==1){
                System.out.println("Attack");
                int temp = hero.attack(monster);
                System.out.println("You attacked and inflicted "+Integer.toString(temp)+" damage to the monster.");
                int monst = monster.attack();
                hero.defence(monst);
                System.out.println("Monster attacked and inflicted "+Integer.toString(monst)+" damage to the You.");
                
            }
            else if(move==2){
                System.out.println("Defence");
                int temp = hero.get_defense();
                System.out.println("Monster attack reduced by "+Integer.toString(temp));
                int monst = monster.attack()-temp;
                if(monst<0)monst=0;
                hero.defence(monst);
                System.out.println("Monster attacked and inflicted "+Integer.toString(monst)+" damage to the You.");
            }
            else if(move==3){
                System.out.println("Special Attack Activated");
                count=0;
                hero.set_special();
                // System.out.println(hero.getClass());
            }
            else{
                System.out.println("Illegal Move");
            }
        }
        return -1;
    }
    int check_user(String name){
        int flag=0;
        int index=-1;
        for(int i=0;i<this.user_index;i++){
            if(this.users[i].get_name().equals(name)==true){
                flag=1;
                index=i;
            }
        }
        if(flag==1){
            return index;
        }
        else{
            return -1;
        }
    }
}
class Display{
    void show(String[] outputs){
        for(int i=0;i<outputs.length;i++){
            System.out.println(Integer.toString(i+1)+") "+outputs[i]);
        }
    }
}
class Heros{
    protected int HP;
    protected int damage;
    protected int defence;
    protected int XP;
    protected int[] HP_value={100,150,200,250};
    protected int special_index;
    protected int defence_delta=0;
    protected int damage_delta=0;
    int attack(Monster monster){
        // System.out.println("special index"+this.special_index);
        monster.defence(this.damage);
        return this.damage;
    }
    void special_update(){
        this.special_index-=1;
    }
    void increase_five(){
        this.HP= (int) Math.round(this.HP*1.05);
    }
    void level_up(){
        this.defence_delta+=1;
        this.damage_delta+=1;
    }
    int get_defense(){
        return this.defence;
    }
    void defence(int damage){
        this.HP-=damage;
    }
    void set_special(){
        this.special_index=3;
    }
    int special(Monster monster){
        return this.HP;
    }
    int get_HP(){
        return this.HP;
    }
    void set_HP(int level){
        this.HP=this.HP_value[level-1];
    }
    int get_XP(){
        return this.XP;
    }
    int monst_kill(Monster monster,int level){
        if(level==1){
            this.HP=100;
        }
        else if(level==2){
            this.HP=150;
        }
        else if(level==3){
            this.HP=200;
        }
        else{
            this.HP=250;
        }
        // System.out.println("monster level "+monster.get_level());
        this.XP+=20*monster.get_level();

        return monster.get_level();
    }
}
class Warrior extends Heros{
    Warrior(){
        this.damage=10;
        this.defence=3;
    }
    @Override
    int special(Monster monster){
        if(this.special_index>0){
            this.damage=15+this.damage_delta;
            this.defence=8+this.defence_delta;
        }
        else{
            this.damage=10+this.damage_delta;;
            this.defence=3+this.defence_delta;
        }
        return 0;
    }
}
class Mage extends Heros{
    Mage(){
        this.damage=5;
        this.defence=5;
    }
    @Override
    int special(Monster monster){
        if(this.special_index>0){
            monster.reduce_five();
        }
        return 0;
    }
}
class Theif extends Heros{
    Theif(){
        this.damage=6;
        this.defence=4;
    }
    @Override
    int special(Monster monster){
        if(this.special_index==3){
            int power = (int) Math.round(monster.get_HP()*0.3);
            System.out.println("You stole "+power+" HP from monster.");
            monster.defence(power);
        }
        return 0;
    }
}
class Healer extends Heros{
    Healer(){
        this.damage=4;
        this.defence=8;
    }
    @Override
    int special(Monster monster){
        if(this.special_index>0){
            this.increase_five();
        }
        return 0;
    }
}
class Monster{
    protected int HP;
    protected int level;
    protected int power;
    int attack(){
        double value=0;
        value=Math.random()*(this.HP/4);
        int output = (int) Math.round(value);
        System.out.println("Monster Next attack power : "+output);
        return output;
    }
    void defence(int damage){
        this.HP-=damage;
    }
    int get_HP(){
        return this.HP;
    }
    int get_level(){
        return this.level;
    }
    void revive(){
        this.HP=100;
    }
    void reduce_five(){
        this.HP=(int) Math.round(this.HP*0.95);;
    }
}
class Goblins extends Monster{
    Goblins(){
        this.HP=100;
        this.level=1;
    }
    @Override
    void revive(){
        this.HP=100;
    }
}
class Zombies extends Monster{
    Zombies(){
        this.HP=100;
        this.level=2;
    }
    @Override
    void revive(){
        this.HP=100;
    }
}
class Fiends extends Monster{
    Fiends(){
        this.HP=200;
        this.level=3;
    }
    @Override
    void revive(){
        this.HP=200;
    }
}
class Lionfang extends Monster{
    Lionfang(){
        this.HP=250;
        this.level=4;
    }
    @Override
    void revive(){
        this.HP=250;
    }
}
class User{
    private String name;
    private int hero;
    User(String name,int hero){
        this.name=name;
        this.hero=hero;
    }
    String get_name(){
        return this.name;
    }
    int get_hero(){
        return this.hero;
    }
}
class LAB_3{
    public static void main(String[] args){
        Game game = new Game();
        game.game_begin();
    }
}