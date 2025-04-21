import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/* @author Микушина Надежда */
public class Game extends JFrame {
    private static Game game_game;
    private long last_frame_time; 
    private Image papich;
    private Image nuts;
    private Image end; 
    private float drop_left = 500; 
    private float drop_top = 200; 
    private float drop_v = 200; 
    private int score = 0;
    
    public static void main(String[] args) throws IOException {
        // Загрузка изображений
        Image papich = ImageIO.read(Game.class.getResourceAsStream("papich.jpg"));
        Image nuts = ImageIO.read(Game.class.getResourceAsStream("nuts.jpg"));
        Image end = ImageIO.read(Game.class.getResourceAsStream("end.jpg"));

        // Создание окна игры
        game_game = new Game();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(200, 50);
        game_game.setSize(800, 600);
        game_game.setResizable(false);

        // Инициализация переменных
        game_game.last_frame_time = System.nanoTime();
        game_game.papich = papich;
        game_game.nuts = nuts;
        game_game.end = end;

        // Создание игрового поля
        GameField game_field = new GameField(game_game); // Передаем ссылку на Game
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // Проверка попадания по объекту
                float drop_right = game_game.drop_left + game_game.nuts.getWidth(null);
                float drop_bottom = game_game.drop_top + game_game.nuts.getHeight(null);
                boolean is_drop = x >= game_game.drop_left && x <= drop_right &&
                                  y >= game_game.drop_top && y <= drop_bottom;

                if (is_drop) {
                    game_game.drop_top = -100;
                    game_game.drop_left = (int) (Math.random() * (game_field.getWidth() - game_game.nuts.getWidth(null)));
                    game_game.drop_v += 10;
                    game_game.score++;
                    game_game.setTitle("Score: " + game_game.score);
                }
            }
        });

        // Добавление игрового поля в окно
        game_game.add(game_field);
        game_game.setVisible(true);
    }

    private void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        // Обновление позиции объекта
        drop_top += drop_v * delta_time;

        // Отрисовка фона и объектов
        g.drawImage(papich, 0, 0, null);
        g.drawImage(nuts, (int) drop_left, (int) drop_top, null);

        // Проверка конца игры
        if (drop_top > getHeight()) {
            g.drawImage(end, 210, 150, null);
        }
    }

    private static class GameField extends JPanel {
        private final Game game;

        public GameField(Game game) {
            this.game = game;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            game.onRepaint(g); // Вызов метода отрисовки
        }
    }
}