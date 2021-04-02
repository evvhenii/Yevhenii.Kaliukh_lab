import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BasicKnowledge {
    public static void main(String[] args){
        //1
        System.out.println(getIntegersFromList(Arrays.asList(3, 5, "dd", 0, -5, "dd", "888")));
        //2
        System.out.println(getFirstNonRepeatedChar("sTreeSS"));
        //3
        System.out.println(digitalRoot(493193));
        //4.1
        System.out.println(countTheNumberOfPairsFor(new int[]{1, 5, 7, -1}, 6));
        //4.2
        System.out.println(countTheNumberOfPairsStream(new int[]{1, 5, 7, -1}, 6));
        //5
        System.out.println(denFriends("Fred:Corwill;Wilfred:Corwill;Barney:Tornbull;Betty:Tornbull;Bjon:Tornbull;Raphael:Corwill;Alfred:Corwill"));
        //6
        System.out.println(nextBiggerNumber(7));
        //7
        System.out.println(toIP(2149583361L));
    }

    public static List getIntegersFromList(List<Object> list){
        return list.stream()
                .filter(Integer.class::isInstance)
                .collect(Collectors.toList());
    }

    public static String getFirstNonRepeatedChar(String str) {
        Map<Character,Integer> numberOfEveryChar = new LinkedHashMap<>(str.length());
        int numberOfCurrentChar = 1;
        for (char c : str.toCharArray()) {
            if(numberOfEveryChar.containsKey(Character.toUpperCase(c))){
                numberOfCurrentChar = numberOfEveryChar.get(Character.toUpperCase(c)) + 1;
                numberOfEveryChar.put(Character.toUpperCase(c), numberOfCurrentChar);
                continue;
            }
            if(numberOfEveryChar.containsKey(Character.toLowerCase(c))){
                numberOfCurrentChar = numberOfEveryChar.get(Character.toLowerCase(c)) + 1;
                numberOfEveryChar.put(Character.toLowerCase(c), numberOfCurrentChar);
                continue;
            }
            numberOfEveryChar.put(c, numberOfCurrentChar);
        }
        for (Map.Entry<Character,Integer> entry : numberOfEveryChar.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey().toString();
            }
        }
        return "";
    }

    public static int digitalRoot(int number) {
        if (number < 10) return number;
        int out = 0;
        while(number > 0) {
            out += number % 10;
            number /= 10;
        }
        return digitalRoot(out);
    }

    public static int countTheNumberOfPairsFor(int[] arr, int sum){
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((arr[i] + arr[j]) == sum) count++;
            }
        }
        return  count;
    }

    public static int countTheNumberOfPairsStream(int[] arr, int sum){
        AtomicInteger i = new AtomicInteger();
        return Arrays.stream(arr).reduce(0, (accumulator1, element1) -> {
            i.getAndIncrement();
            return accumulator1 + Arrays.stream(Arrays.copyOfRange(arr, i.get(), arr.length)).reduce(0, (accumulator2, element2) -> {
                 if(element1 + element2 == sum) accumulator2++;
                 return accumulator2;
            });
        });
    }

    public static String denFriends(String str){
        class Person implements Comparable{
            private final String name;
            private final String surname;

            public Person(String name, String surname) {
                this.name = name;
                this.surname = surname;
            }

            @Override
            public String toString(){
                return "(" + surname + ", " + name + ")";
            }

            @Override
            public int compareTo(Object o) {
                Person p = (Person) o;
                if (surname.compareTo(p.surname) < 0) return -1;
                if (surname.compareTo(p.surname)  > 0) return 1;
                if (name.compareTo(p.name) < 0) return -1;
                if (name.compareTo(p.name)  > 0) return 1;
                return 0;
            }
        }
        List<Person> personList = new ArrayList<>();
        for (String friend : str.split(";")) {
            personList.add(new Person(friend.split(":")[0], friend.split(":")[1]));
        }
        return  personList.stream()
                .sorted()
                .map(Object::toString)
                .reduce("", (a, b)-> a + b);
    }

    public static long nextBiggerNumber(int n) {
        String result = "";
        final String str = String.valueOf(n);

        for (int i = str.length() - 1; i > 0; i--) {
            final int digit = Character.getNumericValue(str.charAt(i - 1));
            if (digit < Character.getNumericValue(str.charAt(i))) {

                int smallestLargerDigit = 9;
                int indexOfSmallestLargerDigit = 0;
                for (int j = i; j < str.length(); j++) {
                    final int actualDigit = Character.getNumericValue(str.charAt(j));
                    if (actualDigit > digit && actualDigit <= smallestLargerDigit) {
                        smallestLargerDigit = actualDigit;
                        indexOfSmallestLargerDigit = j;
                    }
                }

                final ArrayList<Integer> digits = new ArrayList<>();
                for (int j = i - 1; j < str.length(); j++) {
                    if (j != indexOfSmallestLargerDigit) {
                        digits.add(Character.getNumericValue(str.charAt(j)));
                    }
                }

                result += str.substring(0, i - 1);
                result += smallestLargerDigit;

                Collections.sort(digits);
                for (int actualDigit : digits) {
                    result += actualDigit;
                }
                return Long.parseLong(result);
            }
        }
        return -1;
    }

    public static String toIP(long x) {
        return (x >> 24 & 0xFF) + "." + ((x >> 16) & 0xFF) + "." + ((x >> 8) & 0xFF) + "." + (x & 0xFF);
    }

}
