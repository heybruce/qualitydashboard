
fetch('data/orgStructure.json')
  .then(function(resp) {
    return resp.json();
  })
  .then(function(json) {
    return json.OrganizationStructure.ORG;
  })
  .then(renderTable);
function findItem(data, level, id) {
  if(!data[level]) data[level] = [];
  var item = data[level].find(function(item){ return item.MemberId === id});
  if(item) {
    return item;
  }
  data[level].push({MemberId: id});
  return findItem(data, level, id);
}

function forEachChild(el, cb) {
  Array.prototype.forEach.call(el.children, cb);
}

function createElement(tagName, appendToParent, attr) {
  var el = document.createElement(tagName);
  if(attr) {
    Object.keys(attr).forEach(function(name) {
      if(name === 'className') {
        attr[name].split(' ').forEach(function(name) {
          el.classList.add(name);
        });
      } else if(name === 'dataset') {
        Object.keys(attr[name]).forEach(function(key){
          el.dataset[key] = attr[name][key];
        });
      } else {
        el[name] = attr[name];
      }
    });
  }
  if(appendToParent) appendToParent.appendChild(el);
  return el;
};

function findNode(base, id) {
  if(base[id]) return base[id];
  var result;
  Object.keys(base).forEach(function(key){
    var r = findNode(base[key], id);
    if(r) result = r;
  });

  return result;
}

function insertNode(db, node) {
  var id = node.MemberId,
    parentId = node.ParentId;

  var parentNode = findNode(db.tree, parentId);
  if(!parentNode) {
    if(node.OrgLevel !== 1) {
      console.error('Can find parent and unable insert node, please ensure parent node precede before child ', node);
      return db;
    }
    db.tree[parentId] = {};
    parentNode = findNode(db.tree, parentId);
  }
  parentNode[id] = {};
  db.dict[id] = node;

  return db;
}

function buildThead(db, id, node, theads, level) {
  var tr = theads[level],
    item = db.dict[id];

  var elAttr = {
    item: item,
    dataset: {
      name: item ? item.NAME : id,
      id: id
    }
  };
  elAttr.innerText = elAttr.dataset.name;
  var el = createElement('td', tr, elAttr);

  if(item) {
    elAttr.innerText = '';
    for(var i = level + 1; i < theads.length; i++){
      createElement('td', theads[i], elAttr);
    }
  }
  Object.keys(node).forEach(function(key) {
    buildThead(db, key, node[key], theads, level + 1, el)
  });
}

function isEqualRuleSetName(name, item){
  return name === item.RuleSetName;
}

function isRuleSetNameExists(name, ruleSet) {
  var comparator = isEqualRuleSetName.bind(null, name);

  if(ruleSet && ruleSet.RuleSet) {
    if(ruleSet.RuleSet instanceof Array) {
      return !!ruleSet.RuleSet.find(comparator);
    } else {
      return comparator(ruleSet.RuleSet);
    }
    throw new Error('Error');
  }
}

function renderTable(rawData) {

  // start render
  var table = createElement('table', document.body);
  var thead = createElement('thead', table);

  // build tree
  var db = {tree: [], dict: {}};
  rawData.reduce(insertNode, db);

  var theads = [], maxLevels = 3;
  for(var i = 0; i <= maxLevels; i++) {
    var tr = createElement('tr', thead, {className: 'level-' + i});
    theads[i] = tr;
    createElement('td', tr, {innerText: 'Orgnization Level ' + i});
  };
  Object.keys(db.tree).forEach(function(id){
    buildThead(db, id, db.tree[id], theads, 0);
  });

  for(var i = maxLevels-1 ; i >= 0 ; i--) {
    var nextTheads = theads[i+1];

    forEachChild(theads[i], function(el) {
      if((el.item && el.item.OrgLevel === i) || ( i === 0 && el.dataset.id)) {
        var sum = 1, id = el.item ? el.item.MemberId : el.dataset.id;
        forEachChild(nextTheads, function(el) {
          if(el.item && (el.item.ParentId === id)) {
            sum += el.colSpan;
          }
        });
        el.colSpan = sum;
      }
    });
  }

  console.log(db);

  // process rule set
  var ruleList = [];
  function addRuleSets(list, item) {
    if(item instanceof Array) {
      item.forEach(addRuleSets.bind(null, list));
    }
    if(item && item.RuleSetName && list.indexOf(item.RuleSetName) === -1) list.push(item.RuleSetName);
  }
  rawData.forEach(function(data) {
    data.RuleSets.OrgLevel.forEach(function(item, level) {
      if(!ruleList[level]) ruleList[level] = [];
      addRuleSets(ruleList[level], item.AssignedRuleSets.RuleSet);
      addRuleSets(ruleList[level], item.UnassignedRuleSets.RuleSet);
    });
  });

  var flattenRuleList = ruleList.reduce(function(result, list) {
    return result.concat(list.filter(function(item) { return result.indexOf(item) === -1}));
  },[]);

  // build tbody
  var orgList = [];
  forEachChild(theads[theads.length - 1], function(el) {
    if(el.item) orgList.push(el.item);
  });
  var tbody = createElement('tbody', table);
  flattenRuleList.forEach(function(ruleSetName){
    var tr = createElement('tr', tbody);
    createElement('td', tr, {innerText: ruleSetName});
    orgList.forEach(function(item){
      // TODO: implement logic here
      var checked = false;
      if(item.RuleSets && item.RuleSets.OrgLevel) {
        var level;
        var rsList = item.RuleSets.OrgLevel;
        for(var i = rsList.length -1; i >= 0; i--){
          var rs = item.RuleSets.OrgLevel[i];
          if(rs) {
            checked = isRuleSetNameExists(ruleSetName, rs.AssignedRuleSets) && !isRuleSetNameExists(ruleSetName, rs.UnassignedRuleSets);
            if(checked) {
              level = i;
              break;
            }
          }
        }
      } else {
        debugger;
      }
      var options = {};
      if(checked) {
        options.innerText = '✓';
        options.className = 'rule-set level-' + level;
      }

      createElement('td', tr, options);
    });
  })
}
