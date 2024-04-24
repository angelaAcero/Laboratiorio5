/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package laboratiorio5;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.data.general.DefaultPieDataset;

 
/**
 *
 * @author amace
 */
public class chart1 extends javax.swing.JFrame {
DefaultTableModel Estudiantes = new DefaultTableModel();
DefaultTableModel Promedio = new DefaultTableModel();


    public chart1() {
        initComponents();
        txtexiste.setVisible(false);
        
    }

    public void GraficoXY(int i)
    {
        XYSeries series = new XYSeries("XYGraph");
         
        String Nombre = String.valueOf(TablaNotas.getValueAt(i,0)); 
        
         for (int j = 0; j < Integer.parseInt(CantNot.getText()); j++) 
        {
            series.add(j+1, Double.parseDouble((String) TablaNotas.getValueAt(i,j+1)));
            
        }
        
        // Agregar las series de datos
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generar el gráfico
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        Nombre, // Título
        "Consecutivo", // etiqueta para el eje x
        "Nota", // etiqueta para el eje y
        dataset, // Dataset
        PlotOrientation.VERTICAL, // Orientación
        true, // Mostrar leyenda
        true, // Usar tooltips
        false // Configurar para generar URLs
        );
// Generar un archivo con el gráfico
        
    ChartPanel panel = new ChartPanel(chart);
    panel.setMouseWheelEnabled(true);
    panel.setPreferredSize (new Dimension (400,200));
    
    JOptionPane.showMessageDialog(rootPane, panel);
    }
    
    public void GraficoTorta()
    {
        
        int CantAprobados =0;
        int CantReprobados=0;
        double PorcentajeAprobados;
        double PorcentajeReprobados;
        double TotalEstudiantes = Integer.parseInt(CantEs.getText());
        
        for (int i = 0; i < TotalEstudiantes; i++) 
        {
            TablaPromedio.getValueAt(i, 1);
            if (TablaPromedio.getValueAt(i, 2) == "APROBADO :)" )
            {
                CantAprobados++;
            }
            else
            {
                CantReprobados++;
            }
        }

        PorcentajeAprobados = (CantAprobados / TotalEstudiantes)*100;
        PorcentajeReprobados =(CantReprobados/TotalEstudiantes)*100;

        
   // Agregar las series de datos
       DefaultPieDataset dataset2 = new DefaultPieDataset();
        dataset2.setValue("Aprobados " + String.format("%.2f", PorcentajeAprobados) + "%",  CantAprobados);
        dataset2.setValue("Reprobados " + String.format("%.2f", PorcentajeReprobados) + "%", CantReprobados );
        
    // Generar el gráfico y a los jlabel
        
        aprobado.setText("Aprobados: " + String.format("%.2f", PorcentajeAprobados) + "%" );
        reprobado.setText("Reprobados " + String.format("%.2f", PorcentajeReprobados) + "%");
        
        JFreeChart chart2 = ChartFactory.createPieChart(
        "Mortalidad De La Asignatura", // Título
        dataset2, // Dataset
        true, // Mostrar leyenda
        true, // Usar tooltips
        false // Configurar para generar URLs
        );
    // Generar un archivo con el gráfico

        ChartPanel panel2 = new ChartPanel(chart2);
        panel2.setMouseWheelEnabled(true);
        panel2.setPreferredSize (new Dimension (400,200));

        JOptionPane.showMessageDialog(rootPane, panel2);
        
    }
    
    public void Upload(File archivo)
    {
        FileReader leer = null;
        BufferedReader blector = null;
        
        try 
        {
            leer = new FileReader(archivo);
            blector = new BufferedReader(leer);
            
         String linea;
//            linea = blector.readLine();
//            String Arreglo [] = linea.split(",");
            Estudiantes.addColumn("Nombre");

            boolean titulos= true;
            while((linea = blector.readLine()) != null)
            {
                String Arreglo [] = linea.split(",");
                if (titulos == true)
                {
                    for (int i = 1; i < Arreglo.length; i++) 
                    {  
                        Estudiantes.addColumn("Nota " + i);
                    }
                    titulos= false;

                }
                Estudiantes.addRow(Arreglo);
            }
            
             this.TablaNotas.setModel(Estudiantes);
        }
        catch(Exception ex)
        {
                ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(leer !=null)
                {
                    leer.close();
                    CantEs.setText(String.valueOf(TablaNotas.getRowCount()));
                    CantNot.setText(String.valueOf(TablaNotas.getColumnCount()-1));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void saveCVS(File archivo)
    {
        FileWriter fichero =null;
        PrintWriter printear = null;
        
        try 
        {
            fichero = new  FileWriter(archivo);
            printear = new PrintWriter(fichero);
            String linea= "";
            
            for (int i = 0; i < Integer.parseInt(CantEs.getText()); i++)
            {
                for (int j = 0; j < Integer.parseInt(CantNot.getText())+1; j++) 
                {
                     linea = linea + TablaNotas.getValueAt(i,j);
                     
                     if (j < Integer.parseInt(CantNot.getText()))
                     {
                         linea = linea + ",";
                     }
                }
                 printear.println(linea);
                linea = "";
            }
           
           
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (fichero!=null)
                {
                    fichero.close();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
            
    }
    
     public void saveCVSRes(File archivo)
    {
        FileWriter fichero =null;
        PrintWriter printear = null;
        
        try 
        {
            fichero = new  FileWriter(archivo);
            printear = new PrintWriter(fichero);
            String linea= "";
            
            for (int i = 0; i < Integer.parseInt(CantEs.getText()); i++)
            {
                for (int j = 0; j < 3; j++) 
                {
                     linea = linea + TablaPromedio.getValueAt(i,j);
                     
                     if (j < 2)
                     {
                         linea = linea + ",";
                     }
                }
                 printear.println(linea);
                linea = "";
            }
           
           
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (fichero!=null)
                {
                    fichero.close();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
            
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CantEs = new javax.swing.JTextField();
        CantNot = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaNotas = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPromedio = new javax.swing.JTable();
        CalcularFinales = new javax.swing.JButton();
        MostrarHistorico = new javax.swing.JButton();
        NombreBuscar = new javax.swing.JTextField();
        txtexiste = new javax.swing.JLabel();
        Reset = new javax.swing.JButton();
        Mortalidad = new javax.swing.JButton();
        aprobado = new javax.swing.JLabel();
        reprobado = new javax.swing.JLabel();
        cvsIngreso = new javax.swing.JButton();
        save = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        saveres = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cantidad de estudiantes:");

        jLabel2.setText("Cantidad de notas por estudiante: ");

        CantEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CantEsActionPerformed(evt);
            }
        });
        CantEs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CantEsKeyTyped(evt);
            }
        });

        CantNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CantNotKeyTyped(evt);
            }
        });

        jButton1.setText("CREAR TABLA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        TablaNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TablaNotas);

        TablaPromedio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Definitiva", "Resultado"
            }
        ));
        jScrollPane1.setViewportView(TablaPromedio);

        CalcularFinales.setText("Calcular Finales");
        CalcularFinales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalcularFinalesActionPerformed(evt);
            }
        });

        MostrarHistorico.setText("Mostrar Histórico");
        MostrarHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarHistoricoActionPerformed(evt);
            }
        });

        txtexiste.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtexiste.setText("El Usuario No Existe");

        Reset.setText("REINICIAR");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        Mortalidad.setText("Mortalidad");
        Mortalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MortalidadActionPerformed(evt);
            }
        });

        aprobado.setText("Aprobado: ");

        reprobado.setText("Reprobado: ");

        cvsIngreso.setText("Ingresar Archivo");
        cvsIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cvsIngresoActionPerformed(evt);
            }
        });

        save.setText("GUARDAR COMO CSV");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        jLabel3.setText("Nombre del estuidante:");

        saveres.setText("GUARDAR COMO CSV");
        saveres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Mortalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aprobado, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reprobado, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(NombreBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(CantEs, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CantNot, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(81, 81, 81))
                        .addComponent(CalcularFinales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MostrarHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtexiste))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cvsIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(saveres, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                                .addComponent(Reset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(CantEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(CantNot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CalcularFinales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NombreBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtexiste, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MostrarHistorico)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cvsIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(saveres, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Mortalidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(aprobado)
                        .addGap(12, 12, 12)
                        .addComponent(reprobado)))
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CantEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CantEsActionPerformed
   
    }//GEN-LAST:event_CantEsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Estudiantes.addColumn("Nombre");
        for (int i = 1; i <= Integer.parseInt(CantNot.getText()); i++) {    
           Estudiantes.addColumn("Nota " + i);
          
        }
         Estudiantes.setRowCount(Integer.parseInt(CantEs.getText()));
        this.TablaNotas.setModel(Estudiantes);
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CalcularFinalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalcularFinalesActionPerformed
      
        
        Promedio.setRowCount(Integer.parseInt(CantEs.getText()));
        
        Promedio.addColumn("Nombre");
        Promedio.addColumn("Promedio");
        Promedio.addColumn("Resultado");
        
        this.TablaPromedio.setModel(Promedio);
        float Promediofinal = 0;
        String Resultado;

            for (int i = 0; i < Integer.parseInt(CantEs.getText()); i++) 
            {
                String Nombre = String.valueOf(TablaNotas.getValueAt(i,0)); 
                TablaPromedio.setValueAt(Nombre, i, 0);
            float  sumatoria = 0;
            
            for (int j = 0; j < Integer.parseInt(CantNot.getText()); j++) 
            {
                String Prom = (String)TablaNotas.getValueAt(i, j+1);
                double Prom1 =  Double.parseDouble(Prom);
                sumatoria += Prom1;
            }
            
         Promediofinal = sumatoria/Integer.parseInt(CantNot.getText());
         TablaPromedio.setValueAt(Promediofinal, i, 1);
        

         if(Promediofinal < 3.0)
         {
             Resultado = "REPROBADO :(";
         }
         else
         {
             Resultado = "APROBADO :)";
         }

         for (int m = 0; m < Integer.parseInt(CantEs.getText()); m++)
         {
             TablaPromedio.setValueAt(Resultado, i, 2);
         }       
        }

    }//GEN-LAST:event_CalcularFinalesActionPerformed

    private void MostrarHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarHistoricoActionPerformed
        
        String Comparar = NombreBuscar.getText();
        boolean existe = false;
        
        for (int i = 0; i < Integer.parseInt(CantEs.getText()); i++) 
        {
            String Nombre = String.valueOf(TablaNotas.getValueAt(i,0)); 
            
            if (Comparar.equals(Nombre))
            {
                existe = true;
                GraficoXY(i);

            }       
        }
          if (existe == false)
          {
                txtexiste.setVisible(true);
          }
          
        
    }//GEN-LAST:event_MostrarHistoricoActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        CantNot.setText("");
        CantEs.setText("");
        Estudiantes.setColumnCount(0);
        Estudiantes.setRowCount(0);
        this.TablaNotas.setModel(Estudiantes);
        Promedio.setColumnCount(0);
        Promedio.setRowCount(0);
        this.TablaPromedio.setModel(Promedio);
    }//GEN-LAST:event_ResetActionPerformed

    private void CantEsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CantEsKeyTyped
        int key = evt.getKeyChar();
       
       boolean numero = key >= 48 && key <= 57;
       
       if (!numero)
       {
           evt.consume();
       }
    }//GEN-LAST:event_CantEsKeyTyped

    private void CantNotKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CantNotKeyTyped
        int key = evt.getKeyChar();
       
       boolean numero = key >= 48 && key <= 57;
       
       if (!numero)
       {
           evt.consume();
       }
    }//GEN-LAST:event_CantNotKeyTyped

    private void MortalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MortalidadActionPerformed
     GraficoTorta();
    }//GEN-LAST:event_MortalidadActionPerformed

    private void cvsIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cvsIngresoActionPerformed
     
        JFileChooser TraerArchivo = new  JFileChooser();
        FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter ("Archivos tipo csv","csv");
        
        TraerArchivo.setFileFilter(filtroCSV);
        
        int seleccionar = TraerArchivo.showOpenDialog(this);
        
        if (seleccionar == JFileChooser.APPROVE_OPTION)
        {
            File archivo = TraerArchivo.getSelectedFile();
            Upload(archivo);
        }
        
    }//GEN-LAST:event_cvsIngresoActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
         JFileChooser GuardarArchivo = new  JFileChooser();
        FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter ("Archivos tipo csv","csv");
        
        GuardarArchivo.setFileFilter(filtroCSV);
        
        int seleccionar = GuardarArchivo.showOpenDialog(this);
        
        if (seleccionar == JFileChooser.APPROVE_OPTION)
        {
            File archivo = GuardarArchivo.getSelectedFile();
            saveCVS(archivo);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void saveresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveresActionPerformed
       JFileChooser GuardarArchivo = new  JFileChooser();
        FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter ("Archivos tipo csv","csv");
        
        GuardarArchivo.setFileFilter(filtroCSV);
        
        int seleccionar = GuardarArchivo.showOpenDialog(this);
        
        if (seleccionar == JFileChooser.APPROVE_OPTION)
        {
            File archivo = GuardarArchivo.getSelectedFile();
            saveCVSRes(archivo);
        }
    }//GEN-LAST:event_saveresActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chart1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chart1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chart1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chart1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chart1().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CalcularFinales;
    private javax.swing.JTextField CantEs;
    private javax.swing.JTextField CantNot;
    private javax.swing.JButton Mortalidad;
    private javax.swing.JButton MostrarHistorico;
    private javax.swing.JTextField NombreBuscar;
    private javax.swing.JButton Reset;
    private javax.swing.JTable TablaNotas;
    private javax.swing.JTable TablaPromedio;
    private javax.swing.JLabel aprobado;
    private javax.swing.JButton cvsIngreso;
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel reprobado;
    private javax.swing.JButton save;
    private javax.swing.JButton saveres;
    private javax.swing.JLabel txtexiste;
    // End of variables declaration//GEN-END:variables
}
