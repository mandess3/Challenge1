import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {

        // Validador del archivo
        String regex = "([2-9]|[1-4][0-9]|50)\\s([2-9]|[1-4][0-9]|50)\\s([3-9]|[0-9][0-9]{1,2}|[1-4][0-9]{1,3}|5000)\\n[a-zA-Z0-9]{2,50}\\n[a-zA-Z0-9]{2,50}\\n[a-zA-Z0-9]{3,5000}";
        Pattern p = Pattern.compile(regex);

        try {

            String contentStr = Files.readString(Paths.get("in.txt"));
            Matcher m = p.matcher(contentStr);

            // Si el archivo es valido
            if (m.matches()) {
                // Lectura de archivo
                String[] content = contentStr.split("\n");

                // Separacion de lineas
                String[] x = content[0].split(" ");
                int[] lenght = new int[3];

                // Conversion a valores Numericos
                for (int i = 0; i < 3; i++) {
                    lenght[i] = Integer.parseInt(x[i]);

                    if (lenght[i] != content[i + 1].length()) {
                        System.out.println("Numero '" + lenght[i] + "' no concuerda con la longitud de la cadena '" + content[i + 1] + "'");
                        System.exit(0);
                    }
                }


                // Eliminar caracteres consecutivos iguales en el mensaje
                StringBuilder message = new StringBuilder(content[3]);

                for (int i = 1; i < lenght[2]; i++) {

                    while (message.charAt(i - 1) == message.charAt(i)) {
                        message = message.deleteCharAt(i);
                        lenght[2] -= 1;

                        if (lenght[2] == i) {
                            break;
                        }
                    }

                }

                // Creacion de archivo
                FileWriter outFile = new FileWriter("out.txt");

                // Primera instruccion
                for (int i = 0; i + lenght[0] < message.length(); i++) {
                    if (content[1].equals(message.toString().substring(i, i + lenght[0]))) {
                        outFile.write("Si");
                        break;
                    } else {
                        if ((i + lenght[0]) == (message.length() - 1)) {
                            outFile.write("No");
                        }
                    }
                }

                // Segunda instruccion
                for (int i = 0; i + lenght[1] < message.length(); i++) {
                    if (content[2].equals(message.toString().substring(i, i + lenght[1]))) {
                        outFile.write("\nSi");
                        break;
                    } else {
                        if ((i + lenght[1]) == (message.length() - 1)) {
                            outFile.write("\nNo");
                        }
                    }
                }

                outFile.close();
            } else {
                System.out.println("Problema en la estructura del archivo");
            }

        } catch (IOException e) {
            System.out.println("Problema en la lectura o guardado de archivo: " + e.getMessage());
            ;
        }
    }
}