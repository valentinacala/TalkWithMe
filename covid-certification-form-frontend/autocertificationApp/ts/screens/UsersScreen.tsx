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

function UsersScreen(props: Props) {
    const SpacerView = (
        <View style={styles.spacer}></View>
    )

    return(
        <Container style={{backgroundColor: 'white'}}>

        <Content style={styles.content}>
                {SpacerView}
               <Text style={commonStyles.header}>Seleziona il tipo di utente:</Text> 
               {SpacerView}

                <Button  
                    style={commonStyles.button} 
                    onPress={() =>{
                        props.navigation.navigate('Citizen')
                }}>
                    <Text style={commonStyles.buttonText}>{"Utente Cittadino"}</Text></Button>
                {SpacerView}

                <Button 
                    style={commonStyles.buttonPolice} 
                    onPress={() =>{
                        props.navigation.navigate('Police')
                    }
                }>
                    <Text style={commonStyles.buttonText}>{"Utente Controllore"}</Text>
                </Button>
                {SpacerView}

         </Content>
         <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
         {SpacerView}
         {SpacerView}
         {SpacerView}
         </Container>

    );
}

export default  UsersScreen;