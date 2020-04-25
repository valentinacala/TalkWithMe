import { Container, Header, Content, Text, View, Button } from "native-base";
import * as React from "react";
import { TouchableHighlight } from "react-native-gesture-handler";
import { StyleSheet, Alert, Image } from "react-native";
import { commonStyles } from "../utils/commonStyles";

var t = require('tcomb-form-native');

var _ = require('lodash');

const stylesheet = _.cloneDeep(t.form.Form.stylesheet);

stylesheet.textbox.normal.borderWidth = 0;
stylesheet.textbox.error.borderWidth = 0;
stylesheet.textbox.normal.marginBottom = 0;
stylesheet.textbox.error.marginBottom = 0;
stylesheet.textbox.normal.paddingHorizontal = 0
stylesheet.textbox.error.paddingHorizontal = 0

stylesheet.controlLabel.normal.color = "#6E7D99";
stylesheet.controlLabel.error.color = "#6E7D99";
stylesheet.controlLabel.normal.marginBottom = 0;
stylesheet.controlLabel.error.marginBottom = 0;
stylesheet.controlLabel.normal.paddingTop = 16;
stylesheet.controlLabel.error.paddingTop = 16;

stylesheet.textboxView.normal.borderWidth = 0;
stylesheet.textboxView.error.borderWidth = 0;
stylesheet.textboxView.normal.borderRadius = 0;
stylesheet.textboxView.error.borderRadius = 0;
stylesheet.textboxView.normal.borderBottomWidth = 1;
stylesheet.textboxView.error.borderBottomWidth = 1;
stylesheet.textboxView.normal.marginBottom = 5;
stylesheet.textboxView.error.marginBottom = 5;
stylesheet.textboxView.error.borderColor = 'red';
stylesheet.textboxView.normal.borderColor = '#6E7D99';


type Props = {
  navigation: any
};

export default function GuidedFormScreen(props: Props) {  

  var MovingForm = t.form.Form;
  const MovingFormRef = React.useRef<any>(null);
  const Moving = t.struct({
        indirizzoDiPartenza: t.String,              
        indirizzoDiDestinazione: t.String,  
        RegioneDiPartenza: t.String,
        RegioneDiDestinazione: t.String,
        motivoDelloSpostamento: t.String,
        provvedimentoRegionale: t.maybe(t.String),
        dettagliSulloSpostamento: t.String
    });  
  
  var AnagraficaForm = t.form.Form;
  const AnagraficaFormRef = React.useRef<any>(null);
   
  const AnagraficaStruct = {
      nome: t.String,              
      cognome: t.String,  
      dataDiNascita: t.String,
      luogoDiNascita: t.String,
      comuneDiNascita: t.String,
      indirizzoDiResidenza: t.String,              
      comuneDiResidenza: t.String,       
      indirizzoDiDomicilio: t.String,              
      comuneDiDomicilio: t.String
  };
  const Anagrafica = t.struct(AnagraficaStruct);

    var DocumentoForm = t.form.Form;
    const DocumentoFormRef = React.useRef<any>(null);
    const Documento = t.struct({
        tipologiaDiDocumento: t.String,
        numeroDocumento: t.String,
        enteCheHaErogatoIlDocumento: t.String,
        dataDiRilascioDelDocuemento: t.String,
        utenzaTelefonica: t.String
    });

    var DichiarazioniForm = t.form.Form;
    const DichiarazioniFormRef = React.useRef<any>(null);
    const Dichiarazioni = t.struct({
        dichiarazione1: t.Boolean,
        dichiarazione2: t.Boolean,
        dichiarazione3: t.Boolean,
        dichiarazione4: t.Boolean,
    });

    const formOptions = {
        stylesheet: stylesheet,
        fields: {
          birthDate: {
            mode: 'date' // display the Date field as a DatePickerAndroid
          },
          dichiarazione1:{
            label: 'comprovate esigenze lavorative'
          },
          dichiarazione2: {
            label: 'assoluta urgenza (“per trasferimenti in comune diverso”, come previsto dall’art. 1, comma 1, lettera b) del Decreto del Presidente del Consiglio dei Ministri 22 marzo 2020)'
          },
          dichiarazione3:{
            label: 'situazione di necessità (per spostamenti all’interno dello stesso comune o che rivestono carattere di quotidianità o che, comunque, siano effettuati abitualmente in ragione della brevità delle distanze da percorrere)'
          },
          dichiarazione4: {
            label: 'motivi di salute'
          },
          provvedimentoRegionale: {
            help: 'indicare i dettagli del provvedimento regionale che consente lo spostamento'
          }
        }
      };
 
    const onPress = () => {
    if(AnagraficaFormRef.current && DocumentoFormRef.current && MovingFormRef.current && DichiarazioniFormRef.current){
            const AnagraficaValues = AnagraficaFormRef.current.getValue();
            const DocumentoValues = DocumentoFormRef.current.getValue();
            const SpostamentoValues = MovingFormRef.current.getValue();
            const DichiarazioniValues = DichiarazioniFormRef.current.getValue();
            const data = {
                'anagrafica': AnagraficaValues, 
                "documento": DocumentoValues,
                'spostamento': SpostamentoValues,
                'dichiarazioni': DichiarazioniValues
            };

            if(AnagraficaValues === null || DocumentoValues === null || SpostamentoValues === null) {
              Alert.alert('Attenzione! Alcuni campi non sono compilati')
            } else {
              props.navigation.navigate('Form', {data: data, isFormEnabled: false})   
            }
        }
    }

    const SpacerView = (
      <View style={commonStyles.spacer}></View>
  )

    return(
      <Container>
        <Content style={{padding: 16, backgroundColor: 'white'}} bounces={false}>
        <Image style={commonStyles.repImageSmall} source={require("../img/logo_repIt_blue.png")}/>
                <Text style={commonStyles.header}>{"AUTODICHIARAZIONE AI SENSI DEGLI ARTT. 46 E 47 D.P.R. N. 445/2000"}</Text>
            
            {SpacerView}{SpacerView}{SpacerView}
            <Text style={styles.bold}>Anagrafica</Text>
            <AnagraficaForm
                ref={AnagraficaFormRef}
                type={Anagrafica}
                options={formOptions}
            />

            <Text style={styles.bold}>Documento di riconoscimento</Text>
            <DocumentoForm
                ref={DocumentoFormRef}
                type={Documento}
                options={formOptions}
            />

            <Text style={styles.bold}>Dettagli sullo spostamento</Text>
            <MovingForm
                ref={MovingFormRef}
                type={Moving}
                options={formOptions}
            />

            <Text style={styles.bold}>Lo spostamento è determinato da:</Text>
            <DichiarazioniForm
                ref={DichiarazioniFormRef}
                type={Dichiarazioni}
                options={formOptions}
            />
            {SpacerView}
            {SpacerView}
            <Button style={commonStyles.button} onPress={onPress}>
              <Text style={commonStyles.buttonText}>Continua</Text>
            </Button>
            {SpacerView}
            {SpacerView}
            {SpacerView}
          
        </Content>
      </Container>
    )
};

const styles = StyleSheet.create({
    container: {
      justifyContent: 'center',
      marginTop: 50,
      padding: 20,
      backgroundColor: '#ffffff',
    },
    buttonText: {
      fontSize: 18,
      color: 'white',
      alignSelf: 'center'
    },
    button: {
      height: 36,
      backgroundColor: '#48BBEC',
      borderColor: '#48BBEC',
      borderWidth: 1,
      borderRadius: 8,
      marginBottom: 10,
      alignSelf: 'stretch',
      justifyContent: 'center'
    },
    bold: {
      fontWeight: '700', fontSize: 20, paddingVertical: 16
    }
})