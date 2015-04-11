<?xml version="1.0" encoding="UTF-8"?>
<core:Model xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:core="org.eclipse.jwt/core" xmlns:processes="org.eclipse.jwt/processes" name="Workflow" author="jchaline" version="1.0" fileversion="1.3.0">
  <elements xsi:type="processes:Activity" name="Diagram1">
    <ownedComment text="This is a basic activity"/>
    <ownedComment/>
    <nodes xsi:type="processes:InitialNode" name="En cours de saisie" out="//@elements.0/@edges.5"/>
    <nodes xsi:type="processes:FinalNode" name="Lauréat" in="//@elements.0/@edges.4"/>
    <nodes xsi:type="processes:Action" name="Incomplet" in="//@elements.0/@edges.2"/>
    <nodes xsi:type="processes:ForkNode" in="//@elements.0/@edges.6" out="//@elements.0/@edges.0 //@elements.0/@edges.1 //@elements.0/@edges.2 //@elements.0/@edges.3"/>
    <nodes xsi:type="processes:Action" name="Hors conditions (à transmettre)" in="//@elements.0/@edges.3"/>
    <nodes xsi:type="processes:Action" name="Recevable (à transmettre)" in="//@elements.0/@edges.0" out="//@elements.0/@edges.4"/>
    <nodes xsi:type="processes:Action" name="En attente" in="//@elements.0/@edges.1"/>
    <nodes xsi:type="processes:Action" name="Validé candidat" in="//@elements.0/@edges.5" out="//@elements.0/@edges.6"/>
    <edges source="//@elements.0/@nodes.3" target="//@elements.0/@nodes.5"/>
    <edges source="//@elements.0/@nodes.3" target="//@elements.0/@nodes.6"/>
    <edges source="//@elements.0/@nodes.3" target="//@elements.0/@nodes.2">
      <guard name="Mail le dossier est incomplet" icon="D:\projets\work_billetterie_v4\ToolBox\conf\workflowgenerator\images\mail.png" textualdescription="Mail le dossier est incomplet" shortdescription=""/>
    </edges>
    <edges source="//@elements.0/@nodes.3" target="//@elements.0/@nodes.4"/>
    <edges source="//@elements.0/@nodes.5" target="//@elements.0/@nodes.1">
      <guard name="Le dossier est recevable" textualdescription="Le dossier est recevable"/>
    </edges>
    <edges source="//@elements.0/@nodes.0" target="//@elements.0/@nodes.7"/>
    <edges source="//@elements.0/@nodes.7" target="//@elements.0/@nodes.3"/>
    <eventHandler/>
  </elements>
</core:Model>
