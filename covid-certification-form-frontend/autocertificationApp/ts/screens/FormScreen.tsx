import { 
    View, 
    Text, 
    StyleSheet, 
    TextInput, 
    KeyboardAvoidingView, 
    Platform, 
    Alert, 
    SafeAreaView,
    TouchableWithoutFeedback, 
    Keyboard, 
    Image
} from "react-native";
import * as React from "react";
import { Content, Button } from "native-base";
import { commonStyles } from "../utils/commonStyles";
import { useDispatch } from "react-redux";
import { storeCertification } from "../store/actions";
import { Dispatch } from "redux";

const styles = StyleSheet.create({
    spacer: {
        width: '100%', 
        height: 16
    },
    container: {
        flex: 1, 
        flexDirection: 'column', 
        alignContent: 'flex-start', 
        padding: 20,
        backgroundColor: 'white'
    },
    paragraph: {
        flexDirection: 'row', 
        flexWrap: 'wrap'
    },
    text: {marginTop: 3, lineHeight: 20},
    long: {
        width: 200
    }, 
    short: {
        width: 30
    }
});

type Props = {
    navigation: any,
    route: any
}

function FormScreen(props: Props) {
    const dispatch: Dispatch = useDispatch(); 
    const storeCert: typeof storeCertification = (cert: {id: string, data: any}) =>  dispatch(storeCertification(cert));

    const { data, isFormEnabled, isPoliceUser } = props.route.params;
    const isEnabled: boolean = isFormEnabled || false;
    const isPolice: boolean = isPoliceUser || false;

    const SpacerView = (
        <View style={styles.spacer}></View>
    )

    const generateQrCode = () => {
        const dataAsString = JSON.stringify(data)
        console.log(dataAsString)
        if (dataAsString.length < 8200){
            props.navigation.navigate('QrCode', {qrcodeData: dataAsString});
        } else {
            Alert.alert('Il contenuto del form è troppo esteso per poter procedere alla generazione del QR code')
        }
    }

    async function postCertificationData() {
        try {
          let response = await fetch('https://covid-certification-form.eu-gb.mybluemix.net/api/certifications', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
          })

          let json = await response.json()

          storeCert({id: json.id, data: json});
          
          console.log('certification saved');
          
          props.navigation.navigate('Saved', { id: json.id })

        } catch (error) {
          console.error(error);
        }
      }

    return(
        <KeyboardAvoidingView
            behavior={Platform.OS == "ios" ? "padding" : "height"}
            style={{flex: 1, backgroundColor: 'white'}}
        >

        <SafeAreaView style={{flex: 1}}>
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>

        <Content bounces={false} style={styles.container}>
                <Image style={commonStyles.repImageSmall} source={require("../img/logo_repIt_blue.png")}/>
                <Text style={commonStyles.header}>{"AUTODICHIARAZIONE AI SENSI DEGLI ARTT. 46 E 47 D.P.R. N. 445/2000"}</Text>

        <View style={{justifyContent: 'flex-end'}}>
            {SpacerView}
            {SpacerView}
            <View style={styles.paragraph}>
                <Text style={styles.text}>{"Il sottoscritto "}</Text>

                <TextInput editable={isEnabled} style={[commonStyles.textInput]} value={data.anagrafica.nome + ' ' + data.anagrafica.cognome}></TextInput>
                <Text style={styles.text}>{","}</Text>
                <Text style={styles.text}>{" nato il"}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.anagrafica.dataDiNascita}></TextInput>    
                <Text style={styles.text}>{" a "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.anagrafica.luogoDiNascita}></TextInput>
                
                <Text style={styles.text}>{" ("}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput  ]} value={data.anagrafica.comuneDiNascita}></TextInput>
                <Text style={styles.text}>{"),"}</Text>
                <Text style={styles.text}>{" residente in "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.anagrafica.comuneDiResidenza}></TextInput>
                <Text style={styles.text}>{" ("}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput  ]} value={data.anagrafica.comuneDiResidenza}></TextInput>
                <Text style={styles.text}>{"),"}</Text>
                <Text style={styles.text}>{" via "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.anagrafica.indirizzoDiResidenza}></TextInput>
               
                <Text style={styles.text}>{" e domiciliato in "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.anagrafica.comuneDiDomicilio}></TextInput>
                <Text style={styles.text}>{" ("}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput  ]} value={data.anagrafica.comuneDiDomicilio}></TextInput>
                <Text style={styles.text}>{"),"}</Text>
                <Text style={styles.text}>{" via "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]}value={data.anagrafica.indirizzoDiDomicilio}></TextInput>
                
                <Text style={styles.text}>{","}</Text>
                <Text style={styles.text}>{" identificato a mezzo "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.documento.tipologiaDiDocumento}></TextInput>
                <Text style={styles.text}>{" nr. "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.documento.numeroDocumento}></TextInput>
                <Text style={styles.text}>{","}</Text>
                <Text style={styles.text}>{" rilasciato da "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.documento.enteCheHaErogatoIlDocumento}></TextInput>
                
                <Text style={styles.text}>{" in data "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.documento.dataDiRilascioDelDocuemento}></TextInput>

                {/*<View style={{flexDirection: 'row', flexWrap: 'nowrap'}}>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput  ]}></TextInput>
                    <Text style={styles.text}>/</Text>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput  ]}></TextInput>
                    <Text style={styles.text}>/</Text>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput  ]}></TextInput>
                </View>*/}

                <Text style={styles.text}>{","}</Text>
                <Text style={styles.text}>{" utenza telefonica "}</Text>
                <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.documento.utenzaTelefonica}></TextInput>

                <Text style={styles.text}>{","}</Text>
                <Text style={styles.text}>{" consapevole delle conseguenze penali previste in caso di dichiarazioni mendaci a pubblico ufficiale "}</Text>
                <Text style={[{fontWeight: '700'}, styles.text]}>{"(art. 4095 c.p.)"}</Text>
            </View>
            
            {SpacerView}
            <Text style={[styles.text,{fontWeight: '700', textAlign: 'center'}]}>
                {"DICHIARA SOTTO LA PROPRIA RESPONSABILITA'"}
            </Text>

            {SpacerView}

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}]}>{">"}</Text>
                <View style={{flex: 1}}>
                    <Text style={[styles.text, {fontWeight: '700',textDecorationLine: 'underline'}]}>
                        {"di non essere sottoposto alla misura della quarantena ovvero di non essere risultato positivo al COVID-19"}
                    </Text>
                    <Text style={[styles.text, {fontStyle: 'italic'}]}>{"(fatti salvi gli spostamenti disposti dalle Autorità sanitarie)"}</Text>
                </View>
            </View>

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}]}>{">"}</Text>
                <View style={{flexDirection: 'row', flexWrap: 'wrap', flex: 1}}>
                        <Text style={[styles.text, {fontWeight: '700'}]}>
                            {"che lo spostamento è iniziato da "}
                        </Text>
                        <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.spostamento.indirizzoDiPartenza}></TextInput>
                        <Text style={[styles.text, {fontStyle: 'italic'}]}>
                            {"(indicare l'indirizzo da cui è iniziato)"}
                        </Text>
                        <Text style={[styles.text, {fontWeight: '700'}]}>
                            {" con destinazione "}
                        </Text>
                        <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.spostamento.indirizzoDiDestinazione}></TextInput>
                 </View>
            </View>

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}]}>{">"}</Text>
                <Text style={[styles.text, {fontWeight: '700', textDecorationLine: 'underline', flex: 1}]}>
                    {"di essere a conoscenza delle misure di contenimento del contagio vigenti alla data odierna ed adottate ai sensi degli artt. 1 e 2 del decreto legge 25 marzo 2020, n.19, concernenti le limitazioni alle possibilità di spostamento delle persone fisiche all'interno di tutto il territorio nazionale;"}
                </Text>
            </View>

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}]}>{">"}</Text>
                <View style={{flex: 1}}>
                    <Text style={[styles.text, {fontWeight: '700',  textDecorationLine: 'underline'}]}>
                        {"di essere a conoscenza delle ulteriori limitazioni disposte con provvedimenti del Presidente delle Regione "}
                    </Text>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.spostamento.RegioneDiPartenza}></TextInput> 
                    <Text style={[styles.text, {fontStyle: 'italic'}]}>
                        {"(indicare la Regione di partenza)"}
                    </Text>
                    <Text style={[styles.text, {fontWeight: '700',  textDecorationLine: 'underline'}]}>
                        {"e del Presidente della Regione "}
                    </Text>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput]} value={data.spostamento.RegioneDiDestinazione}></TextInput> 
                    <Text style={[styles.text, {fontStyle: 'italic'}]}>
                        {"(indicare la Regione di arrivo)"}
                    </Text>
                    <Text style={[styles.text, {fontWeight: '700',  textDecorationLine: 'underline'}]}>
                        {"e che lo spostamento rientra in uno dei casi consentiti dai medesimi provvedimenti"}
                    </Text>
                    <TextInput editable={isEnabled} style={[commonStyles.textInput ]} value={data.spostamento.provvedimentoRegionale}></TextInput> 
                    <Text style={[styles.text, {fontStyle: 'italic'}]}>
                        {"(indicare quale);"}
                    </Text>
                </View>
            </View>

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8}]}>{">"}</Text>
                <Text style={[styles.text, {fontWeight: '700', textDecorationLine: 'underline', flex: 1}]}>
                    {"di essere a conoscenza delle sanzioni previste dall'art. 4 del decreto legge 25 marzo 2020, n.19;"}
                </Text>
            </View>

            <View style={{paddingVertical: 8, flexDirection: 'row'}}>
                <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}]}>{">"}</Text>
                <View style={{flexDirection: 'column', flex: 1}}>
                    <Text style={[styles.text, {fontWeight: '700'}]}>
                        {"che lo spostamento è determinato da:"}
                    </Text>

                    <View style={{flexDirection: 'row'}}>
                        <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}, data.dichiarazioni.dichiarazione1 && {color: '#1998FF'}]}>{data.dichiarazioni.dichiarazione1 ?"X   -" : "O   -"}</Text>
                        <Text style={[styles.text, {fontWeight: '700', flex: 1}]}>
                            {"comprovate esigenze lavorative;"}
                        </Text>
                    </View>

                    <View style={{flexDirection: 'row'}}>
                        <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}, data.dichiarazioni.dichiarazione2 && {color: '#1998FF'}]}>{data.dichiarazioni.dichiarazione2 ?"X   -" : "O   -"}</Text>
                        <Text style={[styles.text, {fontWeight: '700', flex: 1}]}>
                            {"assoluta urgenza (“per trasferimenti in comune diverso”, come previsto dall’art. 1, comma 1, lettera b) del Decreto del Presidente del Consiglio dei Ministri 22 marzo 2020);"}
                        </Text>
                    </View>

                    <View style={{flexDirection: 'row'}}>
                        <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}, data.dichiarazioni.dichiarazione3 && {color: '#1998FF'}]}>{data.dichiarazioni.dichiarazione3 ?"X   -" : "O   -"}</Text>
                        <Text style={[styles.text, {fontWeight: '700',  flex: 1}]}>
                            {"situazione di necessità (per spostamenti all’interno dello stesso comune o che rivestono carattere di quotidianità o che, comunque, siano effettuati abitualmente in ragione della brevità delle distanze da percorrere);"}
                        </Text>
                    </View>

                    <View style={{flexDirection: 'row'}}>
                        <Text style={[styles.text, {paddingRight: 8, fontWeight: '700'}, data.dichiarazioni.dichiarazione4 && {color: '#1998FF'}]}>{data.dichiarazioni.dichiarazione4 ?"X   -" : "O   -"}</Text>
                        <Text style={[styles.text, {fontWeight: '700'}]}>
                            {"motivi di salute."}
                        </Text>
                    </View>
                </View>
            </View>

            <Text style={[styles.text, {fontWeight: '700',  flex: 1}]}>
                {"A questo riguardo, dichiara che "}
            </Text>
            <TextInput editable={isEnabled} style={[commonStyles.textInput, {width: '100%'}]} numberOfLines={3} multiline={true} value={data.spostamento.dettagliSulloSpostamento}></TextInput>
            <Text style={[styles.text, {fontWeight: '700', fontStyle: 'italic' , flex: 1}]}>
                {" (lavoro presso ..., devo effettuare una visita medica, urgente assistenza a congiunti o a persone con disabilità, o esecuzioni di interventi assistenziali in favore di persone in grave stato di necessità, obblighi di affidamento di minori, denunce di reati, rientro dall’estero, altri motivi particolari, etc....)."}
            </Text>

            {SpacerView}
            {SpacerView}

            {data.id === undefined ?
                isEnabled ? (
                    <Button 
                        style={commonStyles.button} 
                        onPress={() => {
                            Keyboard.dismiss();
                            Alert.alert('L\'autocertificazione è stata salvata');
                        }}
                    ><Text style={commonStyles.buttonText}>Salva l'autodichiarazione</Text></Button>
                ): (
                    <React.Fragment>
                        <Button 
                            style={commonStyles.button} 
                        onPress={() => {
                            props.navigation.goBack()
                        }}><Text style={commonStyles.buttonText}>Modifica l'autodichiarazione</Text></Button>
                        {SpacerView}
                        <Button 
                            style={commonStyles.button} 
                            onPress={() => {postCertificationData();}}
                        ><Text style={commonStyles.buttonText}>Salva l'autodichiarazione</Text></Button>

                    </React.Fragment>
                    
                )
            : (
                <React.Fragment>
                    <Button 
                        style={isPolice ? commonStyles.buttonPolice : commonStyles.button}
                        onPress={() => {props.navigation.goBack()
                    }}>
                        <Text style={commonStyles.buttonText}>Torna indietro</Text>
                    </Button>
                    {isPolice === false && (
                        <React.Fragment>
                            {SpacerView}
                            <Button 
                                style={commonStyles.button}
                                onPress={generateQrCode}>
                                <Text style={commonStyles.buttonText}>{"Genera QR code"}</Text>
                            </Button>
                        </React.Fragment>
                    )}
                </React.Fragment>
            )}
            
            <View style={{width: '100%', height: 50}}/>
            <View style={{ flex : 1 }} />
            </View>

         </Content>
         </TouchableWithoutFeedback>
         </SafeAreaView>
        </KeyboardAvoidingView>
    );
}

export default FormScreen