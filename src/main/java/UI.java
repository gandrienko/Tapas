import TapasSolutionExplorer.Data.DataKeeper;
import TapasSolutionExplorer.UI.CreateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI implements ActionListener {

  String path="C:\\CommonGISprojects\\tracks-avia\\TAPAS\\ATFCM-20210331",
         date="20190801";
  TapasSolutionExplorer.Data.DataKeeper dk=null;

  JLabel lblPath=null;
  JButton bRunSc0=null, bRunSc2=null, bRunCmpSc=null, bSayGoodbye=null;

  public UI() {
    JFrame frame = new JFrame("TAPAS VA component");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    JPanel p=new JPanel();
    BoxLayout boxLayout=new BoxLayout(p,BoxLayout.Y_AXIS);
    p.setLayout(boxLayout);
    p.add(Box.createVerticalStrut(5));
    JLabel jlbl=new JLabel("Path to data:");
    jlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(jlbl);
    lblPath=new JLabel(path,JLabel.CENTER);
    lblPath.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(lblPath);
    p.add(Box.createVerticalStrut(5));
    JSeparator sep=new JSeparator(JSeparator.HORIZONTAL);
    p.add(sep);
    p.add(Box.createVerticalStrut(5));
    bRunSc0=new JButton("run SolutionExplorer for scenario 0 (delays only)");
    bRunSc0.addActionListener(this);
    bRunSc0.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(bRunSc0);
    bRunSc2=new JButton("run SolutionExplorer for scenario 2 (level capping and delays)");
    bRunSc2.addActionListener(this);
    bRunSc2.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(bRunSc2);
    bRunCmpSc=new JButton("run SolutionExplorer for comparing solutions");
    bRunCmpSc.addActionListener(this);
    bRunCmpSc.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(bRunCmpSc);
    p.add(Box.createVerticalStrut(5));
    bSayGoodbye=new JButton("say OK to Tapas");
    bSayGoodbye.addActionListener(this);
    bSayGoodbye.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(bSayGoodbye);
    p.add(Box.createVerticalStrut(5));
    frame.getContentPane().add(p, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }

  public void setPathToData (String path) {
    this.path=path;
    lblPath.setText(path);
    date=path.substring(path.lastIndexOf("\\")+1);
    System.out.println("* date="+path.substring(path.lastIndexOf("\\")+1));
  }

  public void actionPerformed (ActionEvent ae) {
    if (ae.getSource().equals(bSayGoodbye)) {
      Sender sender=new Sender();
      try {
        sender.sendMessage("test","ok");
      } catch (Exception e) {
        System.out.println("exception="+e);
      }
      return;
    }
    if (ae.getSource().equals(bRunSc0)) {
      String fnCapacities=path+"\\0_delays\\scenario_"+date+"_capacities",
              fnDecisions=path+"\\0_delays\\scenario_"+date+"_exp0_decisions",
              fnFlightPlans=path+"\\0_delays\\scenario_"+date+"_exp0_baseline_flight_plans";
      if (!checkFileExists(fnCapacities) || !checkFileExists(fnDecisions) || !checkFileExists(fnFlightPlans))
        return;
      dk=new DataKeeper(fnCapacities,fnDecisions,fnFlightPlans);
      if (dk!=null && !dk.getSectors().isEmpty())
        new CreateUI(dk,false);
      return;
    }
    if (ae.getSource().equals(bRunSc2)) {
      String fnCapacities=path+"\\2_capping_delays\\\\scenario_"+date+"_capping_delays_capacities",
              fnDecisions=path+"\\2_capping_delays\\\\scenario_"+date+"_capping_delays_exp2_decisions",
              fnFlightPlans=path+"\\2_capping_delays\\\\scenario_"+date+"_capping_delays_exp2_baseline_flight_plans";
      if (!checkFileExists(fnCapacities) || !checkFileExists(fnDecisions) || !checkFileExists(fnFlightPlans))
        return;
      dk=new DataKeeper(fnCapacities,fnDecisions,fnFlightPlans);
      if (dk!=null && !dk.getSectors().isEmpty())
        new CreateUI(dk,false);
      return;
    }
    if (ae.getSource().equals(bRunCmpSc)) {
      String fnCapacities=path+"\\0_delays\\scenario_"+date+"_capacities",
             fnFlightPlans=path+"\\0_delays\\scenario_"+date+"_exp0_baseline_flight_plans",
             fnSolutionsShort[]={path+"\\0_delays\\scenario_"+date+"_exp0_solution",
                                 path+"\\1_only_capping\\scenario_"+date+"_only_capping_exp1_solution",
                                 path+"\\2_capping_delays\\scenario_"+date+"_capping_delays_exp2_solution"},
             fnSolutions[]={"solution0_delays="+fnSolutionsShort[0],
                            "solution1_levelCapping="+fnSolutionsShort[1],
                            "solution2_cappingDelays="+fnSolutionsShort[2]};
      if (!checkFileExists(fnCapacities) || !checkFileExists(fnFlightPlans))
        return;
      for (int i=0; i<fnSolutions.length; i++)
        if (!checkFileExists(fnSolutionsShort[i]))
          return;
      dk=new DataKeeper(fnCapacities,fnFlightPlans,fnSolutions);
      if (dk!=null && !dk.getSectors().isEmpty())
        new CreateUI(dk,false);
      return;
    }
  }

  public boolean checkFileExists (String fname) {
    File f=new File(fname+".csv");
    boolean b=f.isFile();
    System.out.println("* checking file "+fname+((b)?" ... ok":" ... NOT FOUND!"));
    return b;
  }
}
