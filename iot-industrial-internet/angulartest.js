describe('Acceptance testing for the AngularJS web interface', function () {
    
    // guide to using these acceptance tests:
    // 1) install Protractor by typing "npm install -g protractor" to the command line
    // 2) then type "webdriver-manager update" to the command line
    // 3) now every time you want to run these tests, first type "webdriver-manager start" to start the test server,
    // choose "Run Project" in NetBeans and then type "protractor conf.js" in the 
    // cd iot-industrial-internet/src/test/java/fi/iot/iiframework/acceptance/ folder
    // 4) make sure to choose "Run Project" in Netbeans again before running the tests a second time
    // or there will be errors in the tests

    it('the web interface should have a title', function () {
        browser.get('http://localhost:8080/#/');
        expect(browser.getTitle()).toEqual('IIFramework');
    });

    it('should be able to move to the sources list by clicking the sidebar button', function () {
        browser.get('http://localhost:8080/#/');
        element(by.partialLinkText('Information Sources')).click();
        expect(element(by.partialLinkText('Add New')).isPresent()).toBe(true);
    });
    
    it('should be able to move to the errors list, view an error and then return to the errors list', function () {
        browser.get('http://localhost:8080/#/');
        element(by.partialLinkText('Errors')).click();
        element(by.partialLinkText('Details')).click();
        element(by.id('backbutton')).click();
        expect(element(by.partialLinkText('Details')).isPresent()).toBe(true);
    });
    
    it('should be able view a sensor and filter its readouts', function () {
        browser.get('http://localhost:8080/#/sources/');
        element(by.partialLinkText('View')).click();
        element(by.partialLinkText('List')).click();
        element(by.model("more")).sendKeys("22.50");
        element(by.model("less")).sendKeys("22.80");
        element(by.id('filter')).click();
        expect(element.all(by.repeater('readout in readouts')).count()).toEqual(10);
    });

    it('should be able create a new information source', function () {
        browser.get('http://localhost:8080/#/sources/');
        element(by.partialLinkText('Add New')).click();
        var name = element(by.model("is.name"));
        var type = element(by.model("is.type"));
        var url = element(by.model("is.url"));
        var frequency = element(by.model("readFrequency_s"));
        name.sendKeys('NewAcceptanceTestSource');
        element(by.cssContainingText('option', 'XML')).click();
        url.sendKeys('http://axwikstr.users.cs.helsinki.fi/data.xml');
        frequency.sendKeys('15400');
        element(by.id('submitbutton')).click();
        element(by.partialLinkText('Configurations')).click();
        expect(element.all(by.repeater('c in configurations')).count()).toEqual(2);
    });
});