import java.util.Scanner;

public class NetworkTransfer {
    public static int getMinimumTime(String s, int sameTime, int partitionTime) {
        int n = s.length();
        int[] dp = new int[n + 1];
        
        // Initialize dp array with max values
        for (int i = 0; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        
        // Calculate the extra time for substrings due to repeated characters
        int[][] extraTime = new int[n][n];
        for (int i = 0; i < n; i++) {
            int[] count = new int[26]; // Track character frequencies in current substring
            for (int j = i; j < n; j++) {
                count[s.charAt(j) - 'a']++;
                
                // Reset extra time calculation for the substring
                extraTime[i][j] = 0;
                for (int k = 0; k < 26; k++) {
                    if (count[k] > 1) {
                        extraTime[i][j] += (count[k] - 1) * sameTime;
                    }
                }
            }
        }

        // Initialize base case
        dp[0] = 0; // Cost of handling an empty string is 0

        // Fill the dp array: dp[i] is the minimum time to transfer s[0..i-1]
        for (int i = 1; i <= n; i++) {
            dp[i] = extraTime[0][i - 1]; // Case where there is no partition before `i`

            for (int j = 1; j < i; j++) {
                dp[i] = Math.min(dp[i], dp[j] + partitionTime + extraTime[j][i - 1]);
            }
        }
        
        return dp[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take inputs for sameTime and partitionTime
        int sameTime = scanner.nextInt();
        int partitionTime = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character

        // Take the string input
        String s = scanner.nextLine();

        // Calculate and output the minimum time
        int result = getMinimumTime(s, sameTime, partitionTime);
        System.out.println(result);

        scanner.close();
    }
}
