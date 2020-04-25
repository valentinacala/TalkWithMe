import { View, Text, Image, StyleSheet, TouchableOpacity, FlatList } from "react-native";
import * as React from "react";
import { Content, Container } from "native-base";
import { commonStyles } from "../utils/commonStyles";
import { useSelector } from "react-redux";
import { certificationSelector } from "../store/reducers/reducer";
import moment from "moment";

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
    text: {marginTop: 3},

});

type Props = {
    navigation: any
};

export default function RecoverOldFormScreen( props: Props) {


    const ownCerts = useSelector(certificationSelector);


    const renderItem = ({ item }: { item: {id: string, data: any}}) => {
        const date = new Date(item.data.sign_date);
        const formatDate = moment(date).format('DD/MM/YYYY, HH:MM')
        return (
            <TouchableOpacity onPress={() => props.navigation.navigate('Form', { data: item.data })}>
                <View style={{paddingVertical: 16}}>
                    <Text style={commonStyles.bold}>{"Id autodichiarazione: " +  item.id}</Text>
                    <Text>{"Nome dichiarante: " + item.data.anagrafica.nome + " " + item.data.anagrafica.cognome}</Text>
                    <Text>{"Data firna dichiarazione: " + formatDate}</Text>
                </View>
            </TouchableOpacity>
        )
    }

    const getSeparator= () => (
        <View style={{width: '100%', height: 1, backgroundColor: '#59A0FF'}}/>
    )

    const SpacerView = (
        <View style={styles.spacer}></View>
    )
    return(
        <Container style={{backgroundColor: 'white'}}>
        <Content bounces={false} style={styles.container}>

            {SpacerView}
            {SpacerView}
                
            <Text>{"Visualizza un'autodichiarazione già compilata:"}</Text>
            {SpacerView}
            <FlatList 
                data={ownCerts}
                renderItem={renderItem}
                ItemSeparatorComponent={getSeparator}
                ListFooterComponent={SpacerView}
                ListEmptyComponent={<Text style={{paddingVertical: 16, textAlign: 'center'}}>Non hai ancora creato nessuna autodichiarazione!</Text>}
            />
        </Content>
        {SpacerView}
        {SpacerView}
        <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
        {SpacerView}
        {SpacerView}
        {SpacerView}
        </Container>
    );
}