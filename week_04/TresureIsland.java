/**
 * url : https://www.acmicpc.net/problem/2589
 *
 * 문제
 * 보물섬 지도를 발견한 후크 선장은 보물을 찾아나섰다. 보물섬 지도는 아래 그림과 같이 직사각형 모양이며 여러 칸으로 나뉘어져 있다. 각 칸은 육지(L)나 바다(W)로 표시되어 있다. 이 지도에서 이동은 상하좌우로 이웃한 육지로만 가능하며, 한 칸 이동하는데 한 시간이 걸린다. 보물은 서로 간에 최단 거리로 이동하는데 있어 가장 긴 시간이 걸리는 육지 두 곳에 나뉘어 묻혀있다. 육지를 나타내는 두 곳 사이를 최단 거리로 이동하려면 같은 곳을 두 번 이상 지나가거나, 멀리 돌아가서는 안된다.
 *
 *
 *
 * 예를 들어 위와 같이 지도가 주어졌다면 보물은 아래 표시된 두 곳에 묻혀 있게 되고, 이 둘 사이의 최단 거리로 이동하는 시간은 8시간이 된다.
 *
 *
 *
 * 보물 지도가 주어질 때, 보물이 묻혀 있는 두 곳 간의 최단 거리로 이동하는 시간을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에는 보물 지도의 세로의 크기와 가로의 크기가 빈칸을 사이에 두고 주어진다. 이어 L과 W로 표시된 보물 지도가 아래의 예와 같이 주어지며, 각 문자 사이에는 빈 칸이 없다. 보물 지도의 가로, 세로의 크기는 각각 50이하이다.
 *
 * 출력
 * 첫째 줄에 보물이 묻혀 있는 두 곳 사이를 최단 거리로 이동하는 시간을 출력한다.
 *
 * testcase 1:
 * 5 7
 * WLLWWWL
 * LLLWLLL
 * LWLWLWW
 * LWLWLLL
 * WLLWLWW
 *
 * output:
 * 8
 */

package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class TresureIsland {
    static int N;
    static int M;
    static char[][] map;
    static boolean[][] visited;

    static int[] xDir = { 1, -1, 0, 0 };
    static int[] yDir = { 0, 0, 1, -1 };

    final static char SEA = 'W';
    final static char GROUND = 'L';

    static class Step {
        int x, y, depth;

        Step(int y, int x, int depth) {
            this.y = y;
            this.x = x;
            this.depth = depth;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final String config = br.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(config, " ");

        N = Integer.valueOf(stringTokenizer.nextToken());
        M = Integer.valueOf(stringTokenizer.nextToken());

        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            final String mapLine = br.readLine();

            for (int j = 0; j < M; j++) {
                map[i][j] = mapLine.charAt(j);
            }
        }

        System.out.println(solve());
    }

    static int solve() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result = Math.max(bfs(i, j), result);
            }
        }

        return result;
    }

    static int bfs(int y, int x) {
        if( map[y][x] == SEA ) { return 0; }
        visited = new boolean[N][M];

        Queue<Step> q = new LinkedList<>();
        q.offer(new Step(y, x, 0));
        visited[y][x] = true;

        int cnt = 0;

        while( !q.isEmpty() ) {
            Step pop = q.poll();
            cnt = Math.max(cnt, pop.depth);

            for (int i = 0; i < 4; i++) {
                final int mx = pop.x + xDir[i], my = pop.y + yDir[i];

                if (isNotRange(my, mx) || visited[my][mx] || map[my][mx] == SEA) { continue; }
                visited[my][mx] = true;

                q.offer(new Step(my, mx, pop.depth + 1));
            }
        }

        return cnt;
    }

    static boolean isNotRange(int y, int x) {
        return x < 0 || y < 0 || x >= M || y >= N;
    }
}
