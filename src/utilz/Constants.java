package utilz;

import Main.Game;

public class Constants {

    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int Attack1 = 0;
        public static final int Dead = 1;
        public static final int Hurt = 2;
        public static final int Idle = 3;
        public static final int Jump = 4;
        public static final int Run = 5;
        
        public static int getAnimationAmount(int player_action){

            if (player_action == Attack1)
                return 5;

            else if (player_action == Hurt)
                return 4;
            
            else if (player_action == Dead)
                return 4;
            
            else if (player_action == Idle)
                return 5;
            
            else if (player_action == Jump)
                return 7;
        
            else if (player_action == Run)
                return 8;

            else
                return 0;
        }
    }

    public static class EnemyConstants{
        public static final int Attack = 0;
        public static final int Dying = 1;
        public static final int Hurt = 2;
        public static final int Idle = 3;
        public static final int Walk = 4;

        public static int getAnimationAmount(int enemy_action){

            if (enemy_action == Attack)
                return 6;

            else if (enemy_action == Dying)
                return 6;

            else if (enemy_action == Hurt)
                return 6;

            else if (enemy_action == Idle)
                return 6;

            else if (enemy_action == Walk)
                return 6;

            else
                return 0;
        }
    }
    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.Scale);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.Scale);
        }
    }
    
}
