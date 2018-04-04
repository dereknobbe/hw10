import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A request handler for a server that performs simple arithmetic operations.
 * <p>
 * <p>CS18000 -- Spring 2018 -- External Communication -- Homework</p>
 */
public final class ArithmeticRequestHandler implements Runnable {
    /**
     * The client socket of this request handler.
     */
    private Socket clientSocket;

    /*
     * Error message constants.
     */

    private static final String ILLEGAL_NUM_ARGUMENTS_ERROR_MESSAGE;

    private static final String ILLEGAL_OPERATION_NAME_ERROR_MESSAGE;

    private static final String ILLEGAL_FIRST_OPERAND_ERROR_MESSAGE;

    private static final String ILLEGAL_SECOND_OPERAND_ERROR_MESSAGE;

    static {
        ILLEGAL_NUM_ARGUMENTS_ERROR_MESSAGE = String.format("%s: requests must include an operation name, and two operands all separated by spaces", ArithmeticProtocol.ILLEGAL_REQUEST);

        ILLEGAL_OPERATION_NAME_ERROR_MESSAGE = String.format("%s: the operation name must be part of the protocol", ArithmeticProtocol.ILLEGAL_REQUEST);

        ILLEGAL_FIRST_OPERAND_ERROR_MESSAGE = String.format("%s: the first operand must be a valid integer", ArithmeticProtocol.ILLEGAL_REQUEST);

        ILLEGAL_SECOND_OPERAND_ERROR_MESSAGE = String.format("%s: the second operand must be a valid integer", ArithmeticProtocol.ILLEGAL_REQUEST);
    } //static

    /**
     * Constructs a newly allocated {@code ArithmeticRequestHandler} object with the specified client socket.
     *
     * @param clientSocket the client socket of this request handler
     * @throws IllegalArgumentException if the {@code clientSocket} argument is {@code null}
     */
    public ArithmeticRequestHandler(Socket clientSocket) throws IllegalArgumentException {
        if (clientSocket == null) {
            throw new IllegalArgumentException("clientSocket argument is null");
        } else {
            this.clientSocket = clientSocket;
        } //end if
    } //ArithmeticRequestHandler

    /**
     * Communicates with the client, and processes their requests until they disconnect.
     */
    @Override
    public void run() {
        //TODO implement run() method
        Scanner serverIn = null;
        PrintWriter serverOut = null;
        String serverMessage = "";
        try {
            serverIn = new Scanner(clientSocket.getInputStream());
            serverOut = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (serverIn.hasNextLine()) {
            boolean error = false;
            String userIn = serverIn.nextLine();
            System.out.println(userIn);
            StringTokenizer st = new StringTokenizer(userIn);
            if (st.countTokens() == 1) {
                serverMessage = ILLEGAL_NUM_ARGUMENTS_ERROR_MESSAGE;
                serverOut.println(serverMessage);
                serverOut.flush();
                continue;
            }
            String operator = "";
            String operand1 = "";
            String operand2 = "";
            if (st.countTokens() == 3) {
                operator = userIn.split("\\s+")[0];
                operator = operator.toUpperCase();
                operand1 = userIn.split("\\s+")[1];
                operand2 = userIn.split("\\s+")[2];
            }
            if (st.countTokens() == 3) {
                if (!operator.equals(ArithmeticProtocol.ADD.toString()) && !operator.equals(ArithmeticProtocol.SUBTRACT.toString()) && !operator.equals(ArithmeticProtocol.MULTIPLY.toString()) && !operator.equals(ArithmeticProtocol.DIVIDE.toString())) {
                    serverMessage = ILLEGAL_OPERATION_NAME_ERROR_MESSAGE;
                    System.out.println("Got here");
                    error = true;
                    serverOut.println(serverMessage);
                    serverOut.flush();
                    continue;
                } else if (operand1.matches("-?\\d+") == false) {
                    serverMessage = ILLEGAL_FIRST_OPERAND_ERROR_MESSAGE;
                    error = true;
                    serverOut.println(serverMessage);
                    serverOut.flush();
                    continue;
                } else if (operand2.matches("-?\\d+") == false) {
                    serverMessage = ILLEGAL_SECOND_OPERAND_ERROR_MESSAGE;
                    error = true;
                    serverOut.println(serverMessage);
                    serverOut.flush();
                    continue;
                }
                if (error == false) {
                    if (operator.equals("ADD")) {
                        int equals;
                        int num1;
                        int num2;
                        try {
                            num1 = Integer.parseInt(operand1);
                            num2 = Integer.parseInt(operand2);
                            equals = num1 + num2;
                            serverOut.println(Integer.toString(equals));
                            serverOut.flush();
                            continue;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (operator.equals("SUBTRACT")) {
                        int equals;
                        int num1;
                        int num2;
                        try {
                            num1 = Integer.parseInt(operand1);
                            num2 = Integer.parseInt(operand2);
                            equals = num1 - num2;
                            serverOut.println(Integer.toString(equals));
                            serverOut.flush();
                            continue;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (operator.equals("MULTIPLY")) {
                        int equals;
                        int num1;
                        int num2;
                        try {
                            num1 = Integer.parseInt(operand1);
                            num2 = Integer.parseInt(operand2);
                            equals = num1 * num2;
                            serverOut.println(Integer.toString(equals));
                            serverOut.flush();
                            continue;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (operator.equals("DIVIDE")) {
                        int equals;
                        int num1;
                        int num2;
                        try {
                            num1 = Integer.parseInt(operand1);
                            num2 = Integer.parseInt(operand2);
                            equals = num1 / num2;
                            serverOut.println(Integer.toString(equals));
                            serverOut.flush();
                            continue;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        serverIn.close();
        serverOut.close();
















        /*Scanner inServer = null;
        PrintWriter outServer = null;
        Scanner inUser = new Scanner(System.in);
        try {
            clientSocket = new Socket("localhost", 50000);
            inServer = new Scanner(clientSocket.getInputStream());
            outServer = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {

        }
        String sendData = inUser.nextLine();
        while (inServer.hasNextLine()) {
            inServer.next(sendData);
            String serverSend = "";
            StringTokenizer st = new StringTokenizer(sendData);
            if (st.countTokens() != 3) {
                serverSend = "ILLEGAL_REQUEST: requests must include an operation name, and two operands all seperated by spaces";
                outServer.println(serverSend);
            }
            String operator = sendData.substring(0,sendData.indexOf(" "));
            String operand1 = sendData.substring(sendData.indexOf(" ") + 1,sendData.lastIndexOf(" "));
            String operand2 = sendData.substring(sendData.lastIndexOf(" ") + 1,sendData.length());
            //if (!operator.equals(ArithmeticProtocol.ADD) && !operator.equals(ArithmeticProtocol.SUBTRACT) && !operator.equals(ArithmeticProtocol.MULTIPLY) && !operator.equals(ArithmeticProtocol.DIVIDE) {

         //   }
        }
        String response;

*/
    } //run
}