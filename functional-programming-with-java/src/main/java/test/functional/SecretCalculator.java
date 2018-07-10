package test.functional;

import java.io.Console;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecretCalculator {

    private static Logger LOG = Logger.getGlobal();

    public static void main(String[] args) {
        new SecretCalculator().start();
    }

    private interface Operation {
        BigInteger operation(BigInteger number1, BigInteger number2);
    }

    public enum OperationType {
        ADD(new Operation() {
            @Override
            public BigInteger operation(BigInteger number1, BigInteger number2) {
                return number1.add(number2);
            }
        }), SUB(new Operation() {
            @Override
            public BigInteger operation(BigInteger number1, BigInteger number2) {
                return number1.subtract(number2);
            }
        }), MUL(new Operation() {
            @Override
            public BigInteger operation(BigInteger number1, BigInteger number2) {
                return number1.multiply(number2);
            }
        }), DIV(new Operation() {
            @Override
            public BigInteger operation(BigInteger number1, BigInteger number2) {
                return number1.divide(number2);
            }
        }), MOD(new Operation() {
            @Override
            public BigInteger operation(BigInteger number1, BigInteger number2) {
                return number1.mod(number2);
            }
        });

        private Operation operation;

        private OperationType(Operation operation) {
            this.operation = operation;
        }

        public Operation getOperation() {
            return operation;
        }

    }

    private void start() {
        try {
            Console console = System.console();

            String userName = console.readLine("User Name > ");
            char[] password = console.readPassword("Password > ");
            byte[] passwordDigest = MessageDigest.getInstance("SHA1")
                    .digest((userName + new String(password)).getBytes());

            if ("8Bwwx+5HFG2piGmZUA1M4aryiwg=".equals(Base64.getEncoder().encodeToString(passwordDigest))) {
                LOG.info("Authentication Successful!!");

                String firstNumber = console.readLine("First Number > ");
                String secondNumber = console.readLine("Second Number (First Number = %s) >", firstNumber);

                String operation = "";
                while (true) {
                    operation = console.readLine("\nOperation on number (%s,%s) (ADD, SUB, MUL, DIV, MOD, SWAP)> ",
                            firstNumber, secondNumber).toUpperCase();
                    
                    if ("SWAP".equals(operation)) {
                        String temp = firstNumber;
                        firstNumber = secondNumber;
                        secondNumber = temp;
                    } else if ("BYE".equals(operation)) {
                        break;
                    } else
                        try {
                            OperationType type = OperationType.valueOf(operation);
                            console.writer().write("Under Operation " + operation + " answer is " + type.getOperation()
                                    .operation(new BigInteger(firstNumber), new BigInteger(secondNumber)));
                        } catch (IllegalArgumentException | NullPointerException e) {
                            LOG.info("Invalid Operation : " + operation);
                        }

                }

            } else {
                LOG.severe("User unauthorized to access");
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, "SHA1 Algorithm Not Found", e);

        }
    }
}
