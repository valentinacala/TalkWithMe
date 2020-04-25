import { View, Image, StyleSheet } from "react-native";
import { Button, Text, Content, Container } from 'native-base'
import * as React from "react";
import { commonStyles } from "../utils/commonStyles";

type Props = {
    navigation: any
};

const styles = StyleSheet.create({
    spacer: {width: '100%', height: 16},
    content: {flex: 1 , flexDirection: 'column', alignContent: 'center',  paddingHorizontal: 16},
})

function PoliceScreen(props: Props) {
    const SpacerView = (
        <View style={styles.spacer}></View>
    )

    return(
        <Container style={{backgroundColor: 'white'}}>

        <Content style={styles.content}>
                {SpacerView}
                
                <Button style={commonStyles.buttonPolice} onPress={() =>{props.navigation.navigate('Recover')}}>
                    <Text style={{textAlign:'center'}}>{"Cerca per id un'autodichiarazione"}</Text>
                </Button>

                {SpacerView}
                <Button style={commonStyles.buttonPolice} onPress={() =>{props.navigation.navigate('SearchedForm')}}>
                    <Text style={{textAlign:'center'}}>{"Visualizza lo storico delle autodichiarazioni"}</Text>
                </Button>

                {SpacerView}
                <Button style={commonStyles.buttonPolice} onPress={() =>{props.navigation.navigate('QrCodeScanner')}}>
                    <Text style={{textAlign:'center'}}>{"Acquisisci mediante QR code"}</Text>
                </Button>

         </Content>
         <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
         {SpacerView}
         {SpacerView}
         {SpacerView}
         </Container>

    );
}

export default PoliceScreen;