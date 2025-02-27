#User function Template for python3
from collections import defaultdict

class Solution:
    def distinctColoring (self, N, r, g, b):
        
        colored = [-1 for i in range(N)]
        cost = [float("inf")]
        all_cost = [r,g,b]
        dp = defaultdict(dict)
        
        def reccur(index,curColor,curCost):
            
            if(index == N):
                return 0

            if(dp[index+1].get(curColor,float("inf")) != float("inf")):
                return dp[index+1][curColor]
                
            min_cost = float("inf")
            for i in range(3):
                if(colored[index-1] != i):
                    colored[index] = i
                    res = reccur(index+1,i,curCost)
                    cost = res + all_cost[i][index]
                    dp[index][i] = cost
                    colored[index] = -1
                    min_cost = min(min_cost,cost)
                    
            return min_cost
                    
        for i in range(3):
            colored[0] = i
            res = reccur(1,i,all_cost[i][0])
            dp[0][i] = res
            print(dp)
        return cost[0]
        
obj  = Solution()
print(obj.distinctColoring(3,[1,1,1],[2,2,2],[3,3,3]))