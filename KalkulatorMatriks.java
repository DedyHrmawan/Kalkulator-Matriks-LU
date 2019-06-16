import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KalkulatorMatriks {
        private boolean CEK = true;
        private boolean INFO = true;
        private static int max = 100;
        private static int decimals =3; 
        private int iDF = 0;
	private JTextArea taA, taB, taC;
        private JLabel statusBar;
	private int n = 4;
	private static NumberFormat nf;
	
public Component buatTampilan(){
        //TEXT AREA UNTUK MASUKAN ANGKA
        taA = new JTextArea();
        taB = new JTextArea();
        taC = new JTextArea();
        
        //membuat panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(MatrixPane("Matrix A", taA));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(MatrixPane("Matrix B", taB));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(MatrixPane("Hasil", taC));;
        
    
        //Operasi button
        JPanel paneBtn = new JPanel();
        paneBtn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        paneBtn.setLayout(new GridLayout(3, 3));
		JButton btnApB = new JButton("A + B ");
		JButton btnAmB = new JButton("A - B ");
		JButton btnAkB = new JButton("A * B ");
		JButton btnInvA = new JButton("invers(A) ");
		JButton btnInvB = new JButton("invers(B) ");
		JButton btnTrnsA = new JButton("transpose(A) ");
                JButton btnTrnsB = new JButton("transpose(B) ");
		JButton btnDetA = new JButton("|A|");
		JButton btnDetB = new JButton("|B| ");
		paneBtn.add(btnApB);
		paneBtn.add(btnAmB);
		paneBtn.add(btnAkB);
		paneBtn.add(btnInvA);
		paneBtn.add(btnInvB);
		paneBtn.add(btnTrnsA);
                paneBtn.add(btnTrnsB);
		paneBtn.add(btnDetA);
		paneBtn.add(btnDetB);
                
                
		/* == Menambahkan BUTTON Listeners untuk memanggil suatu fungsi atau method == */
		btnApB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(Plus(BacaMatrix(taA),
							BacaMatrix(taB)), taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                
                btnAmB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(min(BacaMatrix(taA),
							BacaMatrix(taB)), taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                
                 btnAkB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(kali(BacaMatrix(taA),
							BacaMatrix(taB)), taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                  btnInvA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(Invers(BacaMatrix(taA)),taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                  btnInvB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(Invers(BacaMatrix(taB)),taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                  btnTrnsA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(Transpose(BacaMatrix(taA)),taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                  btnTrnsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					MenampilkanMatrix(Transpose(BacaMatrix(taB)),taC);
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		});
                 btnDetA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					taC.setText("Determinant A: " + nf.format(determinant(BacaMatrix(taA))));
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		}); 
                 btnDetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					taC.setText("Determinant B: " + nf.format(determinant(BacaMatrix(taB))));
				} catch (Exception e) {
					System.err.println("Error: " + e);
				}
			}
		}); 
                  
                  
                  
                 
                 
                // Main Panel
              JPanel pane = new JPanel();
                pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(panel);
		pane.add(paneBtn);
                
                JPanel fpane = new JPanel();
                fpane.setLayout(new BorderLayout());
		fpane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		fpane.add("Center", pane);
		statusBar = new JLabel("Siap");
		fpane.add("South", statusBar);
                
                return fpane;
    }
        //pengaturan matrix panel  
    private JPanel MatrixPane(String str, JTextArea ta) {
		JScrollPane scrollPane = new JScrollPane(ta);
		int size = 200;                

                scrollPane.setPreferredSize(new Dimension(size, size));
		JLabel label = new JLabel(str);
		label.setLabelFor(scrollPane);

		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(label);
		pane.add(scrollPane);

		return pane;
	}
              public static void main(String[] args) {
         JFrame frame= new JFrame("Kalkulator Matrix");
        frame.setSize(new Dimension(800, 200));
        KalkulatorMatriks app = new KalkulatorMatriks();
       Component contents = app.buatTampilan();
		frame.getContentPane().add(contents, BorderLayout.CENTER);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);

		nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(decimals);

	}
//    ----------------------------------------------------
              //akhir dari tampilan
//    ----------------------------------------------------
    
        public double[][] BacaMatrix (JTextArea ta) throws Exception{
        if(CEK){
            System.out.println("Membaca Matriks");
        }
        // Menguraikan Text Area
    String kosong = ta.getText();
    String pisah  = "";
    int i =0;
    int j =0;
    int [] rsize = new int [max];
    
        // mendefinisikan ukuran matriks agar valid
    StringTokenizer ts = new StringTokenizer(kosong, "\n");
		while (ts.hasMoreTokens()) {
			StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
			while (ts2.hasMoreTokens()) {
				ts2.nextToken();
				j++;
			}
			rsize[i] = j;
			i++;
			j = 0;
		}
		statusBar.setText("Ukuran Matriks : " + i + "x" + i);
		if ((CEK) || (INFO)) {
			System.out.println("Ukuran Matriks : " + i);
		}

		for (int c = 0; c < i; c++) {
			if (CEK) {
				System.out.println("i=" + i + "  j=" +rsize[c] + "   Kolom : " + c);
			}

			if (rsize[c] != i) {
				statusBar.setText("Ukuran Matriks yang Dimasukan Tidak Sama");
				throw new Exception("Ukuran Matriks yang Dimasukan Tidak Sama");
			}
		}
		/* == Mengatur Ukuran Matriks == */
		n = i;

		double matrix[][] = new double[n][n];
		i = j = 0;
		pisah = "";

		/* == Melakukan penguraian teks yang sebenarnya sekarang == */
		StringTokenizer st = new StringTokenizer(kosong, "\n");
		while (st.hasMoreTokens()) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken());
			while (st2.hasMoreTokens()) {
				pisah = st2.nextToken();
				try {
					matrix[i][j] = Double.valueOf(pisah).doubleValue();
				} catch (Exception exception) {
					statusBar.setText("Angka Tidak Valid ");
				}
				j++;
			}
			i++;
			j = 0;
		}

		if (CEK) {
			System.out.println("Membaca Matriks:");
                        System.out.println("Ukuran Matriks :" + i);
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					System.out.print("m[" + i + "][" + j + "] = "
							+ matrix[i][j] + "   ");
				}
				System.out.println();
			}
		}           
		return matrix;
    }
        // Menampilkan Matriks di Text Area
    public void MenampilkanMatrix(double [][] matrix, JTextArea ta){
        if( CEK){
        }
        String rstr = "";
		String dv = "";

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				dv = nf.format(matrix[i][j]);
				rstr = rstr.concat(dv + "  ");
			}
			rstr = rstr.concat("\n");
		}
		ta.setText(rstr);
	}
   //Set Penghitungan rumus
        public double [][] Plus (double [][] a, double [][] b) {
		int tmpa = a.length;
		int tmpb = b.length;
		if (tmpa != tmpb) {
			statusBar.setText("Ukuran Matriks Tidak Sama");
		}

		double matrix[][] = new double[tmpa][tmpb];

		for (int i = 0; i < tmpb; i++)
			for (int j = 0; j < tmpb; j++) {
				matrix[i][j] = a[i][j] + b[i][j];
			}

		return matrix;
	}
        public double [][] min (double [][]a, double [][] b) {
            int tmpa = a.length;
            int tmpb = b.length;
            if (tmpa != tmpb) {
			statusBar.setText("Ukuran Matriks Tidak Sama");
		}

		double matrix[][] = new double[tmpa][tmpb];

		for (int i = 0; i < tmpb; i++)
			for (int j = 0; j < tmpb; j++) {
				matrix[i][j] = a[i][j] - b[i][j];
			}

		return matrix;
	}
        public double [][] kali (double [][]a, double [][] b) {
             int tmpa = a.length;
            int tmpb = b.length;
            if (tmpa != tmpb) {
			statusBar.setText("Ukuran Matriks Tidak Sama");
		}

		double matrix[][] = new double[tmpa][tmpb];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b[i].length; j++)
				matrix[i][j] = 0;
                
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j] =bariskolom(a,i,b,j);
			}
		}
		return matrix;
	}
        public double bariskolom(double [][] A, int row, double [][] B, int col){
        	double perkalian = 0;
		for(int i = 0; i < A[row].length; i++)
			perkalian +=A[row][i]*B[i][col];
		return perkalian;
	}
        
        public double[][] Invers (double [][]a) {
		// rumus untuk menghitung matriks
		// inv(A) = 1/det(A) * adj(A)
		
                if (INFO) {
			System.out.println("Mencari Invers...");
		}
		int tma = a.length;

		double m[][] = new double[tma][tma];
		double mm[][] = Adjoint(a);

		double det = determinant(a);
		double dd = 0;

		if (det == 0) {
			statusBar.setText("Determinan sama dengan 0, tidak bisa dibalik.");
			if (INFO) {
				System.out.println("Determinant sama dengan 0, tidak bisa dibalik.");
			}
		} else {
			dd = 1 / det;
		}

		for (int i = 0; i < tma; i++)
			for (int j = 0; j < tma; j++) {
				m[i][j] = dd * mm[i][j];
			}
		return m;
	}
        public double[][] Adjoint(double[][] a) {
		if (INFO) {
			System.out.println("Mencari Adjoint...");
		}
		int tma = a.length;

		double m[][] = new double[tma][tma];

		int ii, jj, ia, ja;
		double det;

		for (int i = 0; i < tma; i++)
			for (int j = 0; j < tma; j++) {
				ia = ja = 0;

				double ap[][] = new double[tma - 1][tma - 1];
				for (ii = 0; ii < tma; ii++) {
					for (jj = 0; jj < tma; jj++) {

						if ((ii != i) && (jj != j)) {
							ap[ia][ja] = a[ii][jj];
							ja++;
						}
					}
					if ((ii != i) && (jj != j)) {
						ia++;
					}
					ja = 0;
				}
				det = determinant(ap);
				m[i][j] = (double) Math.pow(-1, i + j) * det;
			}

		m = Transpose(m);
		return m;
	}
        public double[][] SegitigaAtas(double[][] m) {
		if (INFO) {
			System.out.println("Mengubah Bentuk Ke segitia Atas...");
		}
		double f1 = 0;
		double temp = 0;
		int tma = m.length; 
		int v = 1;
            iDF = 1;
		for (int kol = 0; kol < tma - 1; kol++) {
			for (int bar = kol + 1; bar < tma; bar++) {
                            v = 1;
                            luar: while (m[kol][kol] == 0) // cek jika 0 di diagonal
                            { 
                                if (kol + v >= tma) // cek jika mengganti semua baris
				{
				iDF = 0;
				break luar;
				} else {
                                    for (int c = 0; c < tma; c++) {
                                    temp = m[kol][c];
                                    m[kol][c] = m[kol + v][c]; // switch rows
                                    m[kol + v][c] = temp;
						}
						v++; // mennghitung baris yang diganti
						iDF = iDF * -1; // setiap ganti mengubah determinan
					}
				}
				if (m[kol][kol] != 0) {
					if (CEK) {
                                        System.out.println("Ukuran Matriks = " + tma + "   kolom = " + kol + "   baris= " + bar);
					}

					try {
						f1 = (-1) * m[bar][kol] / m[kol][kol];
						for (int i = kol; i < tma; i++) {
							m[bar][i] = f1 * m[kol][i] + m[bar][i];
						}
					} catch (Exception e) {
						System.out.println("Maaf Masih Sampai Disini");
					}
				}
			}
		}
		return m;
	}
        public double determinant(double[][] matrix) {
		if (INFO) {
			System.out.println("Mencari Determinan...");
		}
		int tma = matrix.length;

		double det = 1;

		matrix = SegitigaAtas(matrix);

		for (int i = 0; i < tma; i++) {
			det = det * matrix[i][i];
		} // Mengalikan diagonal bawah

		det = det * iDF;

		if (INFO) {
			System.out.println("Determinant: " + det);
		}
		return det;
	}
        
        public double[][] Transpose(double[][] a) {
		if (INFO) {
			System.out.println("Mencari Transpose...");
		}
		
		double m[][] = new double[a[0].length][a.length];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				m[j][i] = a[i][j];
		return m;
	}
}