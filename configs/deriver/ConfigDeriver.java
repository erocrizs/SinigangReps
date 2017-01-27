import java.util.*;

public class ConfigDeriver {
	public static void main(String[] args) {
		Scanner in = new Scanner( System.in );
		int n = 24;
	
		ArrayList<int>[] x1 = new ArrayList<int>[ n ];
		ArrayList<int>[] y1 = new ArrayList<int>[ n ];
		ArrayList<int>[] x2 = new ArrayList<int>[ n ];
		ArrayList<int>[] y2 = new ArrayList<int>[ n ];
		int bugged = 0;
		
		in.nextLine();
		in.nextLine();
		while(in.hasNext()) {
			String line = in.nextLine();
			String[] tokens = line.split( "," );
			if( "Mark".equals( tokens[0] ) ) {
				int slide = Integer.parseInt( tokens[2] );
				
				if( x1[ slide ].isEmpty() )
					bugged++;
				
				x1[ slide ].add( Integer.parseInt( tokens[3] ) );
				y1[ slide ].add( Integer.parseInt( tokens[4] ) );
				
				line = in.nextLine();
				tokens = line.split(",");
				x2[ slide ].add( Integer.parseInt( tokens[3] ) );
				y2[ slide ].add( Integer.parseInt( tokens[4] ) );
			}
		}
		
		System.out.println("--config--");
		System.out.println( n + " " + bugged );
		for(int i=0; i<n; i++) {
			if( x1[i].isEmpty() ) continue;
			
			int bugs = x1[i].size();
			System.out.println( i + " " + bugs );
			for(int j=0; j<bugs; j++) {
				System.out.println( x1[i].get(j) + " " + y1[i].get(j) + " " + x2[i].get(j) + " " + y2[i].get(j) );
			}
		}
	}
}