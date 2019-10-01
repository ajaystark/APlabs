import java.util.*;
import java.util.Random;
class Game{
    private int[] tiles;
    private int no_of_tiles;
    private Tile[] game_tile;
    private int tile_index;
    private int snake,vulture,cricket,trampoline;
    private String name;
    private int tile_no;
    private int roll_no;
    private int snake_bite,vulture_bite,trampoline_jumps,cricket_bite;
    Game(int n){

        this.tiles = new int[n];
        this.no_of_tiles = n;
        this.tile_index=0;
        this.game_tile = new Tile[n];
        this.snake = 0;
        this.vulture=0;
        this.cricket=0;
        this.tile_no=0;
        this.roll_no=0;
        this.snake_bite=0;
        this.trampoline_jumps=0;
        this.vulture_bite=0;
        this.cricket_bite=0;
    }
    int get_cricket_bite(){return this.cricket_bite;}
    int get_trampoline_jump(){return this.trampoline_jumps;}
    int get_snake_bite(){
        return this.snake_bite;
    }
    String get_name(){return this.name;}
    int get_roll_no(){return this.roll_no;}
    int get_vulture_bite(){return this.vulture_bite;}
    void setup_tiles(){
        System.out.println("Setting up race track ...");
        Random r = new Random();
        for(int i=0;i<this.no_of_tiles;i++){
            int iswhite = r.nextInt(2);
            if(iswhite==0){
                tiles[i] = 0;
            }
            else{
                int tile_type = r.nextInt(4)+1;
                tiles[i]=tile_type;
            }
        }
    }
    int roll_dice(){
        this.roll_no+=1;
        System.out.print("Roll-"+this.roll_no+": ");
        Random r = new Random();
        int n = r.nextInt(6)+1;
        return n;
    }
    void show_tiles(){
        for(int i=0;i<this.no_of_tiles;i++){
            System.out.print(this.tiles[i]+" ");
        }
        // for(int i=0;i<this.tile_index;i++){
        //     System.out.print(this.game_tile[i].get_step()+" ");
        // }
    }
    void setup() throws SnakeBiteException,VultureBiteException,TrampolineException,CricketBiteException,GameWinException{
        this.setup_tiles();
        // Tile new_tile = new Tile();
        Tile new_snake = new Snake();
        Tile new_vulture = new Vulture();
        Tile new_cricket = new Cricket();
        Tile new_white = new White();
        Tile new_trampoline = new Trampoline();

        for(int i=0;i<this.no_of_tiles;i++){
            if(this.tiles[i]==0){
                this.game_tile[tile_index]= new_white;
            }
            else if(this.tiles[i]==1){
                this.game_tile[tile_index] = new_snake;
                this.snake+=1;
            }
            else if(this.tiles[i]==2){
                this.game_tile[tile_index] = new_vulture;
                this.vulture+=1;
            }
            else if(this.tiles[i]==3){ 
                this.game_tile[tile_index] = new_cricket;
                this.cricket+=1;
            }
            else if(this.tiles[i] == 4){
                this.game_tile[tile_index] = new_trampoline;
                this.trampoline+=1;
            }
            // System.out.print(this.game_tile[tile_index].get_step()+" ");
            this.tile_index+=1;
        }

        System.out.println("Danger: There are "+this.snake+","+this.cricket+", "+this.vulture+", numbers of Snakes, Crickets and Vultures respectively on your track.");
        System.out.println("Danger: Each Snake, Cricket and Vulture can throw you back by "+new_snake.get_step()+", "+new_cricket.get_step()+", "+new_cricket.get_step()+" number of tiles respectively");
        System.out.println("Good news: There are "+this.trampoline+" number of Trampolines on your track!");
        System.out.println("Good news: Each Trampoline can help you advance by "+new_trampoline.get_step()+" number of tiles");
        
        this.play_game();
    }
    void play_game() throws SnakeBiteException,VultureBiteException,TrampolineException,CricketBiteException,GameWinException{
        System.out.println("Enter Player name :");
        Scanner s = new Scanner(System.in);
        this.name = s.nextLine();

        System.out.println("Starting the game with "+this.name+" at Tile "+Integer.toString(tile_no));
        System.out.println("Hit Enter to start the game.");
        s.nextLine();
        System.out.println("Game Started");

        int escape =0;

        while(tile_no!=this.no_of_tiles-1){
            int k = this.roll_dice();
            System.out.println(this.name+" rolled "+k+" at Tile "+Integer.toString(tile_no));
            
            if(tile_no==0 && k==6){
                escape=1;
            }
            if(k==6){
                System.out.println("You get a free roll");
                int p=this.roll_dice();
                System.out.println("You rolled "+p);
                k+=p;
            }
            if(escape==1){
                try{
                    this.shake(k);
                    if(tile_no<0){
                        tile_no=0;
                        escape=0;
                    }
                    if(game_tile[tile_no].get_id()==0)System.out.println("I am a White tile.");
                    if(game_tile[tile_no].get_id()==1)throw new SnakeBiteException(game_tile[tile_no]);
                    if(game_tile[tile_no].get_id()==2)throw new VultureBiteException(game_tile[tile_no]);
                    if(game_tile[tile_no].get_id()==3)throw new CricketBiteException(game_tile[tile_no]);
                    if(game_tile[tile_no].get_id()==4)throw new TrampolineException(game_tile[tile_no]);    
                }
                catch(VultureBiteException e){
                    int temp_step = game_tile[tile_no].get_step();
                    this.tile_no+=game_tile[tile_no].get_step();
                    this.vulture_bite+=1;
                    if(this.tile_no<=0){
                        this.tile_no=0;
                        escape=0;
                    }
                    if(this.tile_no>this.no_of_tiles-1){
                        this.tile_no-=temp_step;
                    }
                    // System.out.println("Yapping...! I am a Vulture, you go back by "+game_tile[tile_no].get_step()*-1+" tiles.");
                }
                catch(SnakeBiteException e){
                    int temp_step = game_tile[tile_no].get_step();
                    this.tile_no+=game_tile[tile_no].get_step();
                    this.snake_bite+=1;
                    if(this.tile_no<=0){
                        this.tile_no=0;
                        escape=0;
                    }
                    if(this.tile_no>this.no_of_tiles-1){
                        this.tile_no-=temp_step;
                    }
                    // System.out.println("Hiss...! I am a Snake, you go back by "+game_tile[tile_no].get_step()*-1+" tiles.");
                }
                catch(TrampolineException e){
                    int temp_step = game_tile[tile_no].get_step();
                    this.tile_no+=game_tile[tile_no].get_step();
                    this.trampoline_jumps+=1;
                    if(this.tile_no<=0){
                        this.tile_no=0;
                        escape=0;
                    }
                    if(this.tile_no>this.no_of_tiles-1){
                        this.tile_no-=temp_step;
                    }
                    // System.out.println("Ping Pong...! I am a Trampoline, you advance by "+game_tile[tile_no].get_step()+" tiles.");
                }
                catch(CricketBiteException e){
                    int temp_step = game_tile[tile_no].get_step();
                    this.tile_no+=game_tile[tile_no].get_step();
                    this.cricket_bite+=1;
                    if(this.tile_no<=0){
                        this.tile_no=0;
                        escape=0;
                    }
                    if(this.tile_no>this.no_of_tiles-1){
                        this.tile_no-=temp_step;
                    }
                    // System.out.println("Chirp...! I am a Cricket, you go back by "+game_tile[tile_no].get_step()*-1+" tiles.");
                }
                finally{
                    System.out.println(this.name+" moved to tile "+this.tile_no);
                    try{
                        if(this.tile_no==this.no_of_tiles-1){
                            throw new GameWinException(this);
                        }
                    }
                    catch(GameWinException e){
                        System.out.println("YOU WON");
                    }
                }
            }
            else{
                System.out.println("OOPs you need 6 to start.");  
            }
        }
    }
    void shake(int temp){
        this.tile_no+=temp;
        if(this.tile_no<=0)this.tile_no=0;
        if(this.tile_no>this.no_of_tiles-1)this.tile_no-=temp;
        System.out.println("At tile "+this.tile_no);
    }
}

class Snake extends Tile{
    Snake(){
        int n = this.generate_random();
        this.step = n*-1;
    }
    @Override
    int get_id(){
        return 1;
    }
}
class Trampoline extends Tile{
    Trampoline(){
        int n = this.generate_random();
        this.step = n;
    }
    @Override
    int get_id(){
        return 4;
    }
}
class Cricket extends Tile{
    Cricket(){
        this.step = this.generate_random()*-1;
    }
    @Override
    int get_id(){
        return 3;
    }
}
class White extends Tile{
    White(){
        this.step = 0;
    }
    @Override
    int get_id(){
        return 0;
    }
}
class Vulture extends Tile{
    Vulture(){
        this.step = this.generate_random()*-1;
    }
    @Override
    int get_id(){
        return 2;
    }
}
class Tile{
    protected int step;
    Tile(){
        this.step = this.generate_random();
    }
    int generate_random(){
        Random r = new Random();
        int n = r.nextInt(10)+1;
        return n;
    }
    int get_step(){
        return this.step;
    }
    int get_id(){
        return 0;
    }
}
class SnakeBiteException extends Exception{
    public SnakeBiteException(Tile snake){
        System.out.println("Hiss...! I am a Snake, you go back by "+snake.get_step()*-1+" tiles.");
    }
}
class CricketBiteException extends Exception{
    public CricketBiteException(Tile cricket){
        System.out.println("Chirp...! I am a Cricket, you go back by "+cricket.get_step()*-1+" tiles.");
    }
}
class VultureBiteException extends Exception{
    public VultureBiteException(Tile vulture){
        System.out.println("Yapping...! I am a Vulture, you go back by "+vulture.get_step()*-1+" tiles.");
    }
}
class TrampolineException extends Exception{
    public TrampolineException(Tile trampoline){
        System.out.println("Ping Pong...! I am a Trampoline, you advance by "+trampoline.get_step()+" tiles.");
    }
}
class GameWinException extends Exception{
    public GameWinException(Game game){
        System.out.println(game.get_name() +" wins the race in "+game.get_roll_no()+" rolls");
        System.out.println("Total Snake Bites = "+game.get_snake_bite());
        System.out.println("Total Vulture Bites = "+game.get_vulture_bite());
        System.out.println("Total Cricket Bites = "+game.get_cricket_bite());
        System.out.println("Total Trampoline Jumps = "+game.get_trampoline_jump());
    }
}
class lab5{
    public static void main(String[] args) throws SnakeBiteException,VultureBiteException,TrampolineException,CricketBiteException,GameWinException{
        Scanner s = new Scanner(System.in);

        System.out.println("Enter number of tiles");
        int n=s.nextInt();

        Game t = new Game(n);
        t.setup();
        // Tile tile = new Tile();
        // throw new TrampolineException(tile);
        // t.show_tiles();
    }      
}
